package com.isaackhor.hermes.model.remote

import com.google.gson.Gson
import com.isaackhor.hermes.model.Notif
import io.reactivex.Single
import io.reactivex.SingleEmitter
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class NotifsApi {
  private val authInterceptor = Interceptor { chain ->
    val orig = chain.request()
    val req = orig.newBuilder().header("Authorization", CRED)
    chain.proceed(req.build())
  }

  private val client =
    OkHttpClient.Builder()
      .addInterceptor(authInterceptor)
      .build()

  private val retrofit =
    Retrofit.Builder()
      .baseUrl(SERVICE_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
      .client(client)
      .build()

  val service = retrofit.create(NotifsService::class.java)

  companion object {
    private val SERVICE_BASE_URL = "https://10.0.2.2:50523/v1/"
    private val CRED = Credentials.basic("test",
      "a7d8321f3c0c182bff05bad818b29c830242c78d0b92541366754348fc4e7282379db823a75ad8d1c43773ccc7245dbf5c3951bf03bc4ed8fc82ecf67bb6fa72")
  }
}