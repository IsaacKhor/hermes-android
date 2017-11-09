package com.isaackhor.hermes.model

data class NotifTopic(
    override val id: Int,
    override val name: String,
    val targets: Set<NotifTarget>
) : NotifGroup