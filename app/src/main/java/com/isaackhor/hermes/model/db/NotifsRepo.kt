package com.isaackhor.hermes.model.db

import android.util.Log
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag
import com.isaackhor.hermes.model.remote.ApiNotif
import com.isaackhor.hermes.model.remote.ApiRetNotif
import com.isaackhor.hermes.model.remote.NotifsApi
import com.isaackhor.hermes.utils.ioThread
import io.reactivex.Completable
import io.reactivex.Single

class NotifsRepo(
  private val db: NotifsDb,
  private val remoteApi: NotifsApi
) {
  private var maxNotifsId = 0
  private var tagsId = 0
  private val service = remoteApi.service

  fun getAllNotifs() = db.getNotifDao().getAllNotifs()
  fun getAllNotifsTiled() = db.getNotifDao().getAllNotifsTiled()
  fun getNotif(id: Int) = db.getNotifDao().getNotif(id)

  fun getAllTags() = db.getTagDao().getAllTags()
  fun getTag(id: Int) = db.getTagDao().getTag(id)
  fun getTags(ids: List<Int>) = db.getTagDao().getTags(ids)

  fun getTagsForNotif(notif: Notif) = db.getNotifTagJoinDao().getTagsForNotif(notif.id)

  private fun insertNotif(notif: ApiRetNotif) = insertNotif(listOf(notif))

  private fun insertNotif(notifs: List<ApiRetNotif>) {
    val nt = notifs.map { Notif(it.id, it.title, it.content) }
    db.getNotifDao().insert(nt)

    // Connect notif <-> tag
    val notifTags = notifs.flatMap { n -> n.tags.map { NotifTagJoin(n.id, it) } }
    db.getNotifTagJoinDao().insert(notifTags)

    // Set id to the maximum we know of
    val max = nt.reduceRight { a, b -> if(a.id > b.id) a else b }.id
    maxNotifsId = maxOf(max, maxNotifsId)
  }

  fun fetchLatestNotifs(): Completable =
    service
      .getNotifsAfterId(maxNotifsId)
      // Insert the returned notif into db (if successful)
      .doOnSuccess(::insertNotif)
      .toCompletable()

  fun addNotif(title: String, content: String, tags: List<NotifTag>): Single<Notif> =
    service
      .addNotif(ApiNotif(title, content, tags.map { it.id }))
      .doOnSuccess(::insertNotif)
      .map { Notif(it.id, it.title, it.content) }

  fun addTag(name: String): Single<NotifTag> {
    val tag = NotifTag(tagsId, name)
    ioThread { db.getTagDao().insert(listOf(tag)) }

    Log.i("NotifsRepo", "Inserting tag: $name")

    return Single.just(tag)
  }

  fun nukeEverything() {

  }
}