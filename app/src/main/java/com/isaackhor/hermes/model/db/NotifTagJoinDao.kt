package com.isaackhor.hermes.model.db

import android.arch.persistence.room.*
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface NotifTagJoinDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(notifTagJoin: List<NotifTagJoin>)

  @Delete
  fun delete(notifTagJoin: NotifTagJoin)

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("""
    SELECT * FROM tags INNER JOIN notif_tag_join ON
      tags.id=notif_tag_join.tagId WHERE
      notif_tag_join.notifId=:notifId
  """)
  fun getTagsForNotif(notifId: Int): Flowable<List<NotifTag>>

  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
  @Query("""
    SELECT * FROM notifications INNER JOIN notif_tag_join ON
      notifications.id=notif_tag_join.notifId WHERE
      notif_tag_join.tagId=:tagId
  """)
  fun getNotifsForTag(tagId: Int): Flowable<List<Notif>>
}