package com.isaackhor.hermes.model

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.immutableSetOf

data class Notif(
    val id: Int,
    val title: String,
    val content: String,
    val targets: ImmutableSet<NotifTarget>,
    val topics: ImmutableSet<NotifTopic>
) {
  companion object {
    val TEST_TARGET = NotifTarget(0, "Target 0")
    val TEST_TOPIC = NotifTopic(0, "Topic 0", immutableSetOf(TEST_TARGET))
    val TEST_NOTIF = Notif(0, "Test", "Test",
        immutableSetOf(TEST_TARGET), immutableSetOf(TEST_TOPIC))
  }
}