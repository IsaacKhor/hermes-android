package com.isaackhor.hermes.model.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.isaackhor.hermes.model.Notif
import io.reactivex.Flowable

@Dao
interface NotifDao {
  @Query("SELECT * FROM notifications")
  fun getAllNotifs(): Flowable<List<Notif>>

  @Query("SELECT * FROM notifications WHERE id=:id")
  fun getNotif(id: Int): Flowable<Notif>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(notifEntities: List<Notif>)
}