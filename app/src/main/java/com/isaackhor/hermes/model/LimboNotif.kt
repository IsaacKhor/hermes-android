package com.isaackhor.hermes.model

/**
 * Notif in limbo; it just got created, and the server hasn't confirmed its
 * existence, so it is hanging in limbo waiting to be confirmed or denied
 * by the server
 */
data class LimboNotif(
    val title: String,
    val content: String,
    val targets: List<NotifTarget>,
    val topics: List<NotifTopic>
)