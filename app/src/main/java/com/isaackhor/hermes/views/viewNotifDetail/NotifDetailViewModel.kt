package com.isaackhor.hermes.views.viewNotifDetail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag
import com.isaackhor.hermes.model.db.NotifsRepo
import io.reactivex.schedulers.Schedulers


class NotifDetailViewModel(
  private val repo: NotifsRepo
) : ViewModel() {
  private var notifId = 0
  val notif = MutableLiveData<Notif>()
  val tags = MutableLiveData<List<NotifTag>>()

  fun setId(id: Int) {
    notifId = id
    repo.getNotif(id)
      .subscribeOn(Schedulers.io())
      .doOnSuccess { notif.postValue(it) }
      .flatMap { repo.getTagsForNotif(it) }
      .doOnSuccess { tags.postValue(it) }
  }
}