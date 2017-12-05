package com.isaackhor.hermes.views.viewSelectTag

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.model.NotifTag
import com.isaackhor.hermes.model.db.NotifsRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SelectTagVM(
  private val repo: NotifsRepo
) : ViewModel() {
  val tags: LiveData<List<NotifTag>> =
    LiveDataReactiveStreams.fromPublisher(
      repo.getAllTags()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()))
}