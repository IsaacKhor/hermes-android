package com.isaackhor.hermes.views.viewSelectTag

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.model.NotifTag
import com.isaackhor.hermes.model.db.NotifsRepo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SelectTagVM(
  private val repo: NotifsRepo
) : ViewModel() {
  val tags = MutableLiveData<List<NotifTag>>().apply { value = emptyList() }
  private val disposables = CompositeDisposable()

  init {
    repo.getAllTags()
      .subscribeOn(Schedulers.io())
      .subscribeBy(onSuccess = { tags.postValue(it) })
      .addTo(disposables)
  }

  override fun onCleared() {
    disposables.dispose()
    super.onCleared()
  }
}