package com.isaackhor.hermes.source

import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTopic
import com.isaackhor.hermes.model.NotifTarget
import io.reactivex.Single
import kotlinx.collections.immutable.*
import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.task

class TestingRepo : DataSource {
  private var targetId = 0
  private var topicId = 0
  private var notifId = 0

  private var topics = mutableListOf<NotifTopic>()
  private var targets = mutableListOf<NotifTarget>()
  private var notifs = mutableListOf<Notif>()

  private val notifsMap: ImmutableMap<Int, Notif>
  private val targetsMap: ImmutableMap<Int, NotifTarget>
  private val topicsMap: ImmutableMap<Int, NotifTopic>

  init {
    for (i in 0..10) generateTarget()
    for (i in 0..10) generateTopic(targets)
    for (i in 0..50) generateNotif(topics, targets)

    notifsMap = immutableMapOf(*notifs.map { n -> Pair(n.id, n) }.toTypedArray())
    targetsMap= immutableMapOf(*targets.map { n -> Pair(n.id, n) }.toTypedArray())
    topicsMap = immutableMapOf(*topics.map { n -> Pair(n.id, n) }.toTypedArray())
  }

  private fun generateNotif(topics: List<NotifTopic>, targets: List<NotifTarget>): Notif {
    val notif = Notif(notifId,
        "Notif $notifId",
        "Content $notifId with a lot more content on a lot of lines that is very long",
        selectRandomElement(targets, 3).toImmutableSet(),
        selectRandomElement(topics, 3).toImmutableSet())
    notifs.add(notif)
    notifId += 1
    return notif
  }

  private fun generateTopic(targets: List<NotifTarget>) : NotifTopic {
    topicId += 1
    val topic = NotifTopic(topicId, "Topic $topicId",
        selectRandomElement(targets, 3).toSet())
    topics.add(topic)
    return topic
  }

  private fun generateTarget() : NotifTarget {
    targetId += 1
    val target =  NotifTarget(targetId, "Target $targetId")
    targets.add(target)
    return target
  }

  private fun <T> selectRandomElement(list: List<T>, i: Int): ImmutableList<T> {
    val ret = mutableListOf<T>()
    while (ret.size < i) {
      ret.add(list[(Math.random() * list.size).toInt()])
    }
    return ret.toImmutableList()
  }

  override fun getNotifs(): Promise<List<Notif>, Exception> = task { notifs }
  override fun getNotif(id: Int): Promise<Notif?, Exception> = task { notifsMap[id] }
  override fun addNotif(notif: Notif): Promise<Notif, Exception> = task { notif }
  override fun fetchNotifs(): Promise<List<Notif>, Exception> = task { notifs }
  override fun getTargets(): Single<List<NotifTarget>> = Single.fromCallable { targets }
  override fun getTarget(id: Int): Single<NotifTarget> = Single.fromCallable { targetsMap[id] }
  override fun addTarget(target: NotifTarget): Single<NotifTarget> = Single.fromCallable { target }
  override fun getTopics(): Single<List<NotifTopic>> = Single.fromCallable { topics }
  override fun getTopic(id: Int): Single<NotifTopic> = Single.fromCallable { topicsMap[id] }
  override fun addTopic(topic: NotifTopic): Single<NotifTopic> = Single.fromCallable { topic }

  companion object {
    val INSTANCE = TestingRepo()
  }
}