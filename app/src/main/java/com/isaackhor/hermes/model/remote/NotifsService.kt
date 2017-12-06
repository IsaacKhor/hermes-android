package com.isaackhor.hermes.model.remote

import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface NotifsService {

  @GET("/notifs")
  fun getNotifsAfterId(@Query("after") id: Int): Single<List<ApiRetNotif>>

  @GET("/notifs?after=0")
  fun getAllNotifs(): Single<List<ApiRetNotif>>

  @GET("/notifs/{id}")
  fun getNotifWithId(@Path("id") notifId: Int): Maybe<ApiRetNotif>

  @POST("/notifs")
  fun addNotif(@Body notif: ApiNotif): Single<ApiRetNotif>

  @GET("/tags")
  fun getAllTags(): Single<List<NotifTag>>

  @GET("/tags/{id}")
  fun getTagWithId(@Path("id") tagId: Int): Maybe<NotifTag>

  @POST("/tags")
  fun addTag(@Body tag: ApiTag): Single<NotifTag>

  @PUT("/tags/{id}")
  fun updateTag(@Body tag: NotifTag): Single<NotifTag>

  @DELETE("/tags/{id}")
  fun deleteTag(@Body tag: NotifTag): Completable
}