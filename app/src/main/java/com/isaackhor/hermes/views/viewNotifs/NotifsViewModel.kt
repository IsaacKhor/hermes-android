package com.isaackhor.hermes.views.viewNotifs

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTopic
import com.isaackhor.hermes.source.NotifsRepo
import com.isaackhor.hermes.utils.SingleLiveEvent

class NotifsViewModel(): ViewModel() {
  private var repo: NotifsRepo? = null
  var notifs = MutableLiveData<List<Notif>>()

  val filteredNotifs = MutableLiveData<List<Notif>>()
  val filterMode = MutableLiveData<FilterMode>()
  val filterTargets = MutableLiveData<List<Target>>()
  val filterTopics = MutableLiveData<List<NotifTopic>>()

  val openNotifDetailsEvent = SingleLiveEvent<Int>() // Int is notifId
  val newNotifEvent = SingleLiveEvent<Void>()

  val dataLoading = MutableLiveData<Boolean>()
  val snackbarMsg = MutableLiveData<String>()

  init {
    filterMode.value = FilterMode.TARGET
    repo?.getNotifs()?.subscribe { n -> notifs.value = n }
    dataLoading.value = false
  }

  fun loadNotifs(showLoadingUi: Boolean, forceFetch: Boolean) {
    if (showLoadingUi) dataLoading.value = true
    //TODO setup snackbar message on error
//    if (forceFetch) repo?.fetchRemote()?.subscribe() { _ -> snackbarMsg.value = "Error" }

    repo?.getNotifs()?.subscribe { res ->
      Log.i("NotifsViewModel", "Loading data success")
      notifs.postValue(res)
      // TODO implement filtering
      filteredNotifs.postValue(res)
      dataLoading.postValue(false)
    }
  }

  fun modifyFilter() {}

  fun addNewNotif() = newNotifEvent.call()
  fun openNotifDetails(notifId: Int) {
    openNotifDetailsEvent.value = notifId
  }

  fun setRepo(repo: NotifsRepo) { this.repo = repo }
}