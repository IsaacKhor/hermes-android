package com.isaackhor.hermes.model.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag

@Entity(
  tableName = "notif_tag_join",
  foreignKeys = arrayOf(
    ForeignKey(entity = Notif::class,
      parentColumns = arrayOf("id"), childColumns = arrayOf("notifId")),
    ForeignKey(entity = NotifTag::class,
      parentColumns = arrayOf("id"), childColumns = arrayOf("tagId"))
  )
) data class NotifTagJoin(
  @PrimaryKey(autoGenerate = true)
  val id: Int,
  val notifId: Int,
  val tagId: Int
)