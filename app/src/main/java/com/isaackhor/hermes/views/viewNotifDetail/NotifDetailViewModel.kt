package com.isaackhor.hermes.views.viewNotifDetail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.source.NotifsRepo


class NotifDetailViewModel(
    private val repo: NotifsRepo
) : ViewModel() {
  private var notifId = 0
  val notif = MutableLiveData<Notif>()

  fun setId(id: Int) {
    notifId = id
    repo.getNotif(id).subscribe { n ->
      notif.postValue(n)
    }
  }
}