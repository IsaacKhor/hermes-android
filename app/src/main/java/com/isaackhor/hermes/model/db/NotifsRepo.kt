package com.isaackhor.hermes.model.db

import android.util.Log
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag
import com.isaackhor.hermes.model.remote.NotifsApi
import com.isaackhor.hermes.utils.ioThread
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

class NotifsRepo(
  private val db: NotifsDb,
  private val remoteApi: NotifsApi
) {
  private var notifsId = 0
  private var tagsId = 0
  private val service = remoteApi.service

  fun getAllNotifs() = db.getNotifDao().getAllNotifs()
  fun getAllNotifsTiled() = db.getNotifDao().getAllNotifsTiled()
  fun getNotif(id: Int) = db.getNotifDao().getNotif(id)

  fun getAllTags() = db.getTagDao().getAllTags()
  fun getTag(id: Int) = db.getTagDao().getTag(id)
  fun getTags(ids: List<Int>) = db.getTagDao().getTags(ids)

  fun getTagsForNotif(notif: Notif) = db.getNotifTagJoinDao().getTagsForNotif(notif.id)

  fun fetchRemote(): Completable {
    return service.getNotifsAfterId(notifsId)
      .doOnSuccess { notifs ->
        val nt = notifs.map { Notif(it.id, it.title, it.content) }
        db.getNotifDao().insert(nt)

        // Connect notif <-> tag
        val map = notifs.flatMap { n -> n.tags.map { t -> NotifTagJoin(n.id, t) } }
        db.getNotifTagJoinDao().insert(map)
      }
      .toCompletable()
  }

  fun addNotif(title: String, content: String, tags: List<NotifTag>): Single<Notif> {
    val tagJoins = tags.map { NotifTagJoin(notifsId, it.id) }
    val notif = Notif(notifsId, title, content)

    ioThread {
      db.getNotifDao().insert(listOf(notif))
      db.getNotifTagJoinDao().insert(tagJoins)
    }

    Log.i("NotifsRepo", "Inserting notif: $title")

    notifsId += 1
    return Single.just(notif)
  }

  fun addTag(name: String): Single<NotifTag> {
    val tag = NotifTag(tagsId, name)
    ioThread { db.getTagDao().insert(listOf(tag)) }

    Log.i("NotifsRepo", "Inserting tag: $name")

    return Single.just(tag)
  }

  fun nukeEverything() {

  }
}