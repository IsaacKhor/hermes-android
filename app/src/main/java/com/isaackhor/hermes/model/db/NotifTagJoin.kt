package com.isaackhor.hermes.model.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag

@Entity(
  tableName = "notif_tag_join",
  primaryKeys = ["notifId", "tagId"],
  foreignKeys = [
    ForeignKey(entity = Notif::class,
      parentColumns = arrayOf("id"), childColumns = arrayOf("notifId")),
    ForeignKey(entity = NotifTag::class,
      parentColumns = arrayOf("id"), childColumns = arrayOf("tagId"))
  ]
) data class NotifTagJoin(
  val notifId: Int,
  val tagId: Int
)