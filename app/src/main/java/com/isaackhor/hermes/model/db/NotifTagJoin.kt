package com.isaackhor.hermes.model.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag

@Entity(
  tableName = "notif_tag_join",
  indices = [Index("tagId")],
  primaryKeys = ["notifId", "tagId"],
  foreignKeys = [
    ForeignKey(entity = Notif::class,
      parentColumns = arrayOf("id"), childColumns = arrayOf("notifId"),
      onUpdate = ForeignKey.NO_ACTION, onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = NotifTag::class,
      parentColumns = arrayOf("id"), childColumns = arrayOf("tagId"),
      onUpdate = ForeignKey.NO_ACTION, onDelete = ForeignKey.CASCADE)
  ]
) data class NotifTagJoin(
  val notifId: Int,
  val tagId: Int
)