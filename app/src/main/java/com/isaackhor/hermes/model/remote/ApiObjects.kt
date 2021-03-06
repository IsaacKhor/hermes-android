package com.isaackhor.hermes.model.remote

import com.google.gson.annotations.SerializedName

data class ApiNotif(
  @SerializedName("title")
  val title: String,

  @SerializedName("content")
  val content: String,

  @SerializedName("tags")
  val tags: List<Int>
)

data class ApiRetNotif(
  @SerializedName("id")
  val id: Int,

  @SerializedName("time")
  val time: String,

  @SerializedName("title")
  val title: String,

  @SerializedName("content")
  val content: String,

  @SerializedName("tags")
  val tags: List<Int>
)

data class ApiTag(
  @SerializedName("name")
  val name: String
)