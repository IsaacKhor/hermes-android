package com.isaackhor.hermes.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "notifications")
data class Notif(
  @PrimaryKey
  val id: Int,
  val title: String,
  val content: String
)