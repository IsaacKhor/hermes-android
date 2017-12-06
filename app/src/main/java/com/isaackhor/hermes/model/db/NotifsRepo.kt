package com.isaackhor.hermes.model.db

import android.util.Log
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag
import com.isaackhor.hermes.model.remote.NotifsApi
import com.isaackhor.hermes.utils.ioThread
import io.reactivex.Single

class NotifsRepo(
  private val db: NotifsDb,
  private val remoteApi: NotifsApi
) {
  private var notifsId = 100
  private var tagsId = 100

  fun getAllNotifs() = db.getNotifDao().getAllNotifs()
  fun getAllNotifsTiled() = db.getNotifDao().getAllNotifsTiled()
  fun getNotif(id: Int) = db.getNotifDao().getNotif(id)

  fun getAllTags() = db.getTagDao().getAllTags()
  fun getTag(id: Int) = db.getTagDao().getTag(id)
  fun getTags(ids: List<Int>) = db.getTagDao().getTags(ids)

  fun getTagsForNotif(notif: Notif) = db.getNotifTagJoinDao().getTagsForNotif(notif.id)
  fun fetchRemote() = remoteApi.retrieveNewNotifs()
  fun refreshTags() = remoteApi.fetchTags()

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