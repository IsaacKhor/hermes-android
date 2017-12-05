package com.isaackhor.hermes.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tags")
data class NotifTag(
  @PrimaryKey
  val id: Int,
  val name: String
)