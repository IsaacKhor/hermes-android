package com.isaackhor.hermes.model.db

import android.arch.persistence.room.*
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag
import io.reactivex.Single

@Dao
interface NotifTagJoinDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(notifTagJoin: List<NotifTagJoin>)

  @Delete
  fun delete(notifTagJoin: NotifTagJoin)

  @Query("""
    SELECT * FROM tags INNER JOIN notif_tag_join ON
      tags.id=notif_tag_join.tagId WHERE
      notif_tag_join.notifId=:notifId
  """)
  fun getTagsForNotif(notifId: Int): Single<List<NotifTag>>

  @Query("""
    SELECT * FROM notifications INNER JOIN notif_tag_join ON
      notifications.id=notif_tag_join.notifId WHERE
      notif_tag_join.tagId=:tagId
  """)
  fun getNotifsForTag(tagId: Int): Single<List<Notif>>
}