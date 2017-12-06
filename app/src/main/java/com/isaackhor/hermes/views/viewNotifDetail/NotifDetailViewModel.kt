package com.isaackhor.hermes.views.viewNotifDetail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.model.NotifTag
import com.isaackhor.hermes.model.db.NotifsRepo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


class NotifDetailViewModel(
  private val repo: NotifsRepo
) : ViewModel() {
  val notif = MutableLiveData<Notif>()
  val tags = MutableLiveData<List<NotifTag>>()

  private val disposable = CompositeDisposable()

  fun setId(id: Int) {
    repo.getNotif(id)
      .subscribeOn(Schedulers.io())
      .doOnNext { notif.postValue(it) }
      .flatMap { repo.getTagsForNotif(it) }
      .subscribeBy { tags.postValue(it) }
      .addTo(disposable)
  }

  override fun onCleared() = disposable.dispose()
}