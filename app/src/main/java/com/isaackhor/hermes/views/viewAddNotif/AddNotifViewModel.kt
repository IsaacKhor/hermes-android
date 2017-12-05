package com.isaackhor.hermes.views.viewAddNotif

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.R
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag
import com.isaackhor.hermes.model.db.NotifsRepo
import com.isaackhor.hermes.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.collections.immutable.toImmutableList

class AddNotifViewModel(
  private val repo: NotifsRepo
) : ViewModel() {
  val title = MutableLiveData<String>()
  val content = MutableLiveData<String>()
  val tags = MutableLiveData<List<NotifTag>>()
  val snackbarMsg = SingleLiveEvent<Int>()
  val isSendingNotif = MutableLiveData<Boolean>().also { it.value = false }
  val onNotifSendSuccess = SingleLiveEvent<Notif>()

  private val disposable = CompositeDisposable()

  fun addNewNotif() {
    isSendingNotif.value = true
    repo.addNotif(
      title.value ?: "No title",
      content.value ?: "",
      tags.value ?: listOf())
      .subscribeBy(
        onSuccess = { onNotifSendSuccess.postValue(it) },
        onError = {
          isSendingNotif.value = false
          snackbarMsg.value = R.string.send_notif_fail
        })
      .addTo(disposable)
  }

  fun setNewTagsFromId(ids: List<Int>) {
    repo.getTags(ids)
      .subscribeOn(Schedulers.io())
      .subscribeBy(
        onSuccess = { tags.postValue(it.toImmutableList()) },
        onError = { snackbarMsg.postValue(R.string.retrieve_topics_fail) })
      .addTo(disposable)
  }

  override fun onCleared() {
    disposable.dispose()
    super.onCleared()
  }
}