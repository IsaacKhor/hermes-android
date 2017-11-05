package com.isaackhor.hermes.views.viewNotifDetail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.source.NotifsRepo


class NotifDetailViewModel() : ViewModel() {
  private var repo: NotifsRepo? = null
  private var notifId = 0
  val notif = MutableLiveData<Notif>()

  fun setRepo(repo: NotifsRepo) {
    this.repo = repo
  }

  fun setId(id: Int) {
    notifId = id
    repo?.getNotif(id)?.success { n ->
      notif.postValue(n)
    }
  }
}