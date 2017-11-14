package com.isaackhor.hermes.views.viewSelectTargetTopic

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.model.NotifGroup
import com.isaackhor.hermes.model.NotifTarget
import com.isaackhor.hermes.model.NotifTopic
import com.isaackhor.hermes.source.NotifsRepo
import java.lang.IllegalArgumentException

class SelectTTViewModel(
    val repo: NotifsRepo
) : ViewModel() {
  val groups = MutableLiveData<List<NotifGroup>>().apply { value = emptyList() }
  lateinit var mode: String

  fun setNewMode(new: String) {
    mode = new
    val obs = when (mode) {
      MODE_TARGET -> repo.getTargets()
      MODE_TOPIC -> repo.getTopics()
      else -> throw IllegalArgumentException("Unknown mode: $mode")
    }
    obs.subscribe { l -> groups.postValue(l) }
  }

  companion object {
    val MODE_TARGET = "target"
    val MODE_TOPIC = "topic"
  }
}