package com.isaackhor.hermes.views.viewAddNotif

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTarget
import com.isaackhor.hermes.model.NotifTopic
import com.isaackhor.hermes.source.NotifsRepo
import com.isaackhor.hermes.utils.SingleLiveEvent

class AddNotifViewModel : ViewModel() {
  private lateinit var repo: NotifsRepo

  val title = MutableLiveData<String>()
  val content = MutableLiveData<String>()
  val targets = MutableLiveData<List<NotifTarget>>()
  val topics = MutableLiveData<List<NotifTopic>>()

  val onNewNotifEvent = SingleLiveEvent<Void>()

  fun setRepo(repo: NotifsRepo) {
    this.repo = repo
  }

  fun addNewNotif(new: Notif) {
    repo.addNotif(new)
    onNewNotifEvent.call()
  }
}