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
  override fun getNotifs(): Single<List<Notif>> {
    return localSource.getNotifs()
  }

  override fun getNotif(id: Int): Single<Notif> {
    return localSource.getNotif(id)
  }

  override fun addNotif(title: String, content: String,
                        targets: List<NotifTarget>, topics: List<NotifTopic>): Single<Notif> {
    return localSource.addNotif(title, content, targets, topics)
  }

  override fun getTargets(): Single<List<NotifTarget>> {
    return localSource.getTargets()
  }

  override fun getTarget(id: Int): Single<NotifTarget> {
    return localSource.getTarget(id)
  }

  override fun addTarget(title: String): Single<NotifTarget> {
    return localSource.addTarget(title)
  }

  override fun getTopics(): Single<List<NotifTopic>> {
    return localSource.getTopics()
  }

  override fun getTopic(id: Int): Single<NotifTopic> {
    return localSource.getTopic(id)
  }

  override fun addTopic(title: String, targets: List<NotifTarget>): Single<NotifTopic> {
    return localSource.addTopic(title, targets)
  }

  override fun fetchRemote(): Single<Unit> {
    return localSource.fetchRemote()
  }


  companion object {
    val TESTING = NotifsRepo(TestingRepo.INSTANCE, TestingRepo.INSTANCE)
  }
}