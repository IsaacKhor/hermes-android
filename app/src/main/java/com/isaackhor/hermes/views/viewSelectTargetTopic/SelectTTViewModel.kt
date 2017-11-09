package com.isaackhor.hermes.views.viewSelectTargetTopic

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.model.NotifTarget
import com.isaackhor.hermes.model.NotifTopic
import com.isaackhor.hermes.source.NotifsRepo

class SelectTTViewModel(
    val repo: NotifsRepo
) : ViewModel() {
  val targets = MutableLiveData<NotifTarget>()
  val topics = MutableLiveData<NotifTopic>()
}