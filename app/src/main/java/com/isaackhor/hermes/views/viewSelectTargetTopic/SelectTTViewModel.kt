package com.isaackhor.hermes.views.viewSelectTargetTopic

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.model.NotifTarget
import com.isaackhor.hermes.model.NotifTopic

class SelectTTViewModel : ViewModel() {
  val targets = MutableLiveData<NotifTarget>()
  val topics = MutableLiveData<NotifTopic>()
}