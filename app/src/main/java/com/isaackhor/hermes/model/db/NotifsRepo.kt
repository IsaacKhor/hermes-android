package com.isaackhor.hermes.model.db

import android.util.Log
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag
import com.isaackhor.hermes.utils.ioThread
import io.reactivex.Single

class NotifsRepo(
  private val db: NotifsDb,
  private val remoteSource: RemoteNotifs
) {
  private var notifsId = 100
  private var tagsId = 100

  fun getAllNotifs(): Single<List<Notif>> {
    return db.getNotifDao().getAllNotifs()
  }

  fun getNotif(id: Int): Single<Notif> {
    return db.getNotifDao().getNotif(id)
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

  fun getAllTags(): Single<List<NotifTag>> {
    return db.getTagDao().getAllTags()
  }

  fun getTag(id: Int): Single<NotifTag> {
    return db.getTagDao().getTag(id)
  }

  fun getTags(ids: List<Int>): Single<List<NotifTag>> {
    return db.getTagDao().getTags(ids)
  }

  fun addTag(name: String): Single<NotifTag> {
    val tag = NotifTag(tagsId, name)
    ioThread { db.getTagDao().insert(listOf(tag)) }

    Log.i("NotifsRepo", "Inserting tag: $name")

    return Single.just(tag)
  }

  fun getTagsForNotif(notif: Notif): Single<List<NotifTag>> {
    return db.getNotifTagJoinDao().getTagsForNotif(notif.id)
  }

  fun fetchRemote(): Single<Unit> {
    return remoteSource.fetch()
  }

  fun nukeEverything() {

  }
}