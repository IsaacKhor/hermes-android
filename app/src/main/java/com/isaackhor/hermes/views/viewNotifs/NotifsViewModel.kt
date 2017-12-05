package com.isaackhor.hermes.views.viewNotifs

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.R
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.db.NotifsRepo
import com.isaackhor.hermes.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class NotifsViewModel(
  private val repo: NotifsRepo
) : ViewModel() {

  var notifs: LiveData<List<Notif>> =
    LiveDataReactiveStreams.fromPublisher(
      repo.getAllNotifs()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
    )

  val openNotifDetailsEvent = SingleLiveEvent<Int>() // Int is notifId
  val newNotifEvent = SingleLiveEvent<Void>()

  val dataLoading = MutableLiveData<Boolean>()
  val snackbar = MutableLiveData<Int>()

  init {
    dataLoading.value = false
  }

  fun loadNotifs(showLoadingUi: Boolean, forceFetch: Boolean) {
    if (showLoadingUi) dataLoading.value = true

    repo.fetchRemote().subscribeBy(
      onSuccess = { dataLoading.postValue(false) },
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