package com.isaackhor.hermes.views.viewAddNotif

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.isaackhor.hermes.R
import com.isaackhor.hermes.model.LimboNotif
import com.isaackhor.hermes.model.NotifTarget
import com.isaackhor.hermes.model.NotifTopic
import com.isaackhor.hermes.source.NotifsRepo
import com.isaackhor.hermes.utils.SingleLiveEvent
import io.reactivex.rxkotlin.mergeAllSingles
import io.reactivex.rxkotlin.toObservable
import kotlinx.collections.immutable.toImmutableList

class AddNotifViewModel(
    private val repo: NotifsRepo
) : ViewModel() {
  val title = MutableLiveData<String>()
  val content = MutableLiveData<String>()
  val targets = MutableLiveData<List<NotifTarget>>()
  val topics = MutableLiveData<List<NotifTopic>>()

  val onNewNotifEvent = SingleLiveEvent<LimboNotif>()
  val snackbarMsg = SingleLiveEvent<Int>()

  fun addNewNotif() {
    onNewNotifEvent.value =
        LimboNotif(
            title.value ?: "No title",
            content.value ?: "No content",
            targets.value ?: listOf(),
            topics.value ?: listOf())
  }

  fun setNewTopicsFromId(ids: List<Int>) {
    ids.map { repo.getTopic(it) }
        .toObservable()
        .mergeAllSingles()
        .toList()
        .subscribe(
            { topics.postValue(it.toImmutableList()) },
            { snackbarMsg.postValue(R.string.retrieve_topics_fail) })
  }

  fun setNewTargetsFromId(ids: List<Int>) {
    ids.map { repo.getTarget(it) }
        .toObservable()
        .mergeAllSingles()
        .toList()
        .subscribe(
            { targets.postValue(it.toImmutableList()) },
            { snackbarMsg.postValue(R.string.retrieve_targets_fail) })
  }
}