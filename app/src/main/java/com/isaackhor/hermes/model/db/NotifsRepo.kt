package com.isaackhor.hermes.model.db

import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle
import io.reactivex.schedulers.Schedulers

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
    val tagJoins = tags.map { NotifTagJoin(0, notifsId, it.id) }
    db.getNotifTagJoinDao().insert(tagJoins)

    val notif = Notif(notifsId, title, content)
    db.getNotifDao().insert(listOf(notif))
    notifsId += 1

    return Single.fromCallable { notif }.subscribeOn(Schedulers.io())
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

  fun addTag(name: String, tags: List<NotifTag>): Single<NotifTag> {
    val tag = NotifTag(tagsId, name)
    db.getTagDao().insert(listOf(tag))
    return Single.just(tag)
  }

  fun getTagsForNotif(notif: Notif): Single<List<NotifTag>> {
    return db.getNotifTagJoinDao().getTagsForNotif(notif.id)
  }

  fun fetchRemote(): Single<Unit> {
    return remoteSource.fetch()
  }
}