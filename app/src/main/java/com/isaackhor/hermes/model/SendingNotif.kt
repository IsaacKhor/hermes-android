package com.isaackhor.hermes.model

import com.google.gson.annotations.SerializedName


data class SendingNotif(
  @SerializedName("")
  val title: String,
  val content: String,
  val tags: List<Int>
)