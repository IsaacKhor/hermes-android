package com.isaackhor.hermes.views.viewAddNotif

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.model.LimboNotif
import com.isaackhor.hermes.model.NotifTarget
import com.isaackhor.hermes.model.NotifTopic
import com.isaackhor.hermes.utils.SingleLiveEvent

class AddNotifViewModel : ViewModel() {
  val title = MutableLiveData<String>()
  val content = MutableLiveData<String>()
  val targets = MutableLiveData<List<NotifTarget>>()
  val topics = MutableLiveData<List<NotifTopic>>()

  val onNewNotifEvent = SingleLiveEvent<LimboNotif>()
  val snackbarMsg = SingleLiveEvent<Int>()

  fun addNewNotif() {
    onNewNotifEvent.value =
        LimboNotif(
            title.value ?: "Empty title",
            content.value ?: "Empty content",
            targets.value ?: listOf(),
            topics.value ?: listOf())
  }
}