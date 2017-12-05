package com.isaackhor.hermes.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag
import com.isaackhor.hermes.utils.SingletonHolder

@Database(
  entities = [NotifTagJoin::class, Notif::class, NotifTag::class],
  version = 1)
abstract class NotifsDb : RoomDatabase() {

  abstract fun getNotifDao(): NotifDao
  abstract fun getTagDao(): NotifTagDao
  abstract fun getNotifTagJoinDao(): NotifTagJoinDao

  companion object : SingletonHolder<NotifsDb, Context>({
    Room.databaseBuilder(
      it.applicationContext, NotifsDb::class.java, Companion.DB_NAME).build()
  }) {
    private val DB_NAME = "notifs.db"
  }
}