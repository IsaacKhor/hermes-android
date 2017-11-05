package com.isaackhor.hermes.source

import android.util.Log
import com.isaackhor.hermes.model.Notif
import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.deferred

open class NotifsRepo(
    private val localSource: DataSource,
    private val remoteSource: DataSource
) : DataSource {
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