package com.isaackhor.hermes.source

import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTarget
import com.isaackhor.hermes.model.NotifTopic
import io.reactivex.Single
import nl.komponents.kovenant.Promise

interface DataSource {
  fun getNotifs(): Promise<List<Notif>, Exception>
  fun getNotif(id: Int): Promise<Notif?, Exception>
  fun addNotif(notif: Notif) : Promise<Notif, Exception>

  fun getTargets(): Single<List<NotifTarget>>
  fun getTarget(id: Int): Single<NotifTarget>
  fun addTarget(target: NotifTarget): Single<NotifTarget>

  fun getTopics(): Single<List<NotifTopic>>
  fun getTopic(id: Int): Single<NotifTopic>
  fun addTopic(topic: NotifTopic): Single<NotifTopic>

  fun fetchNotifs(): Promise<List<Notif>, Exception>
}