package com.isaackhor.hermes.source

import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTarget
import com.isaackhor.hermes.model.NotifTopic
import io.reactivex.Single
import nl.komponents.kovenant.Promise

interface DataSource {
  fun getNotifs(): Single<List<Notif>>
  fun getNotif(id: Int): Single<Notif>
  fun addNotif(title: String, content: String,
               targets: List<NotifTarget>, topics: List<NotifTopic>): Single<Notif>

  fun getTargets(): Single<List<NotifTarget>>
  fun getTarget(id: Int): Single<NotifTarget>
  fun addTarget(title: String): Single<NotifTarget>

  fun getTopics(): Single<List<NotifTopic>>
  fun getTopic(id: Int): Single<NotifTopic>
  fun addTopic(title: String, targets: List<NotifTarget>): Single<NotifTopic>

  fun fetchRemote(): Single<Unit>
}