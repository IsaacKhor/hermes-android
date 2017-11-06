package com.isaackhor.hermes.source

import android.util.Log
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTarget
import com.isaackhor.hermes.model.NotifTopic
import io.reactivex.Single
import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.deferred

open class NotifsRepo(
    private val localSource: DataSource,
    private val remoteSource: DataSource
) : DataSource {
  override fun getTargets(): Single<List<NotifTarget>> {
    return localSource.getTargets()
  }

  override fun getTarget(id: Int): Single<NotifTarget> {
    return localSource.getTarget(id)
  }

  override fun addTarget(target: NotifTarget): Single<NotifTarget> {
    return localSource.addTarget(target)
  }

  override fun getTopics(): Single<List<NotifTopic>> {
    return localSource.getTopics()
  }

  override fun getTopic(id: Int): Single<NotifTopic> {
    return localSource.getTopic(id)
  }

  override fun addTopic(topic: NotifTopic): Single<NotifTopic> {
    return localSource.addTopic(topic)
  }

  private val cache = mutableMapOf<Int, Notif>()
  var isCacheDirty = true

  override fun getNotifs(): Promise<List<Notif>, Exception> {
    return localSource.getNotifs()
//    val deferred = deferred<List<Notif>, Exception>()
//
//    if (!isCacheDirty && cache.isNotEmpty()) deferred.resolve(cache.values.toList())
//
//    // Load from remote
//    refreshCacheIfDirty()
//
//    // Load from local source
//    localSource.getNotifs().success { res ->
//      res.forEach { r -> cache.put(r.id, r) }
//      deferred.resolve(res)
//    } fail { e ->
//      deferred.reject(e)
//    }
//
//    return deferred.promise
  }

  override fun getNotif(id: Int): Promise<Notif?, Exception> {
    return localSource.getNotif(id)
  }

  override fun addNotif(notif: Notif): Promise<Notif, Exception> {
    return localSource.addNotif(notif)
  }

  override fun fetchNotifs(): Promise<List<Notif>, Exception> {
    return localSource.fetchNotifs()
  }

  fun refreshCacheIfDirty() {
    if (isCacheDirty) {
      fetchNotifs()
          .success { res -> res.forEach { r -> cache.put(r.id, r) } }
          .fail { Log.i("NotifsRepo", "Failed to fetch remote data") }
    }
  }

  companion object {
    val TESTING = NotifsRepo(TestingRepo.INSTANCE, TestingRepo.INSTANCE)
  }
}