package com.isaackhor.hermes.views.viewNotifs

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.isaackhor.hermes.R
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.db.NotifsRepo
import com.isaackhor.hermes.utils.SingleLiveEvent
import io.reactivex.rxkotlin.subscribeBy

class NotifsViewModel(
  private val repo: NotifsRepo
) : ViewModel() {

  var notifs: LiveData<PagedList<Notif>> =
    repo.getAllNotifsTiled().create(0,
      PagedList.Config.Builder()
        .setPageSize(50)
        .setPrefetchDistance(50)
        .build())

  val openNotifDetailsEvent = SingleLiveEvent<Int>() // Int is notifId
  val newNotifEvent = SingleLiveEvent<Void>()

  val dataLoading = MutableLiveData<Boolean>()
  val snackbar = MutableLiveData<Int>()

  init {
    dataLoading.value = false
  }

  fun loadNotifs(showLoadingUi: Boolean, forceFetch: Boolean) {
    if (showLoadingUi) dataLoading.value = true

    repo.fetchLatestNotifs().subscribeBy(
      onComplete = { dataLoading.postValue(false) },
      onError = {
        snackbar.postValue(R.string.retrieve_notifs_fail)
        dataLoading.postValue(false)
      }
    )
  }

  fun modifyFilter() {}

  fun addNewNotif() = newNotifEvent.call()

  fun openNotifDetails(notifId: Int) {
    openNotifDetailsEvent.value = notifId
  }
}