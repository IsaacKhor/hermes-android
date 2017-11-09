package com.isaackhor.hermes.model

data class NotifTopic(
    val id: Int,
    val name: String,
    val targets: Set<NotifTarget>
) : NotifGroup