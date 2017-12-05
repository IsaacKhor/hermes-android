package com.isaackhor.hermes.model.db

import android.arch.persistence.room.*
import com.isaackhor.hermes.model.NotifTag
import io.reactivex.Single

@Dao
interface NotifTagDao {
  @Query("SELECT * FROM tags")
  fun getAllTags(): Single<List<NotifTag>>

  @Query("SELECT * FROM tags WHERE id=:id")
  fun getTag(id: Int): Single<NotifTag>

  @Query("SELECT * FROM tags WHERE id IN(:ids)")
  fun getTags(ids: List<Int>): Single<List<NotifTag>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(tagEntities: List<NotifTag>)

  @Update(onConflict = OnConflictStrategy.REPLACE)
  fun update(tagEntities: List<NotifTag>)

  @Delete
  fun delete(tagEntities: List<NotifTag>)
}