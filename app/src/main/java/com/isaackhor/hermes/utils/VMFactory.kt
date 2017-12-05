package com.isaackhor.hermes.utils

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.isaackhor.hermes.model.db.NotifsDb
import com.isaackhor.hermes.model.db.NotifsRepo
import com.isaackhor.hermes.model.db.RemoteNotifs
import com.isaackhor.hermes.views.viewAddNotif.AddNotifViewModel
import com.isaackhor.hermes.views.viewNotifDetail.NotifDetailViewModel
import com.isaackhor.hermes.views.viewNotifs.NotifsViewModel
import com.isaackhor.hermes.views.viewSelectTag.SelectTagVM

class VMFactory private constructor(
  private val repo: NotifsRepo
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return with(modelClass) {
      when {
        isAssignableFrom(AddNotifViewModel::class.java) -> AddNotifViewModel(repo)
        isAssignableFrom(NotifDetailViewModel::class.java) -> NotifDetailViewModel(repo)
        isAssignableFrom(NotifsViewModel::class.java) -> NotifsViewModel(repo)
        isAssignableFrom(SelectTagVM::class.java) -> SelectTagVM(repo)
        else -> throw IllegalArgumentException("Unknown class: ${modelClass.name}")
      }
    } as T
  }

  companion object : SingletonHolder<VMFactory, Application>({
    VMFactory(
      NotifsRepo(
        NotifsDb.getInstance(it.applicationContext),
        RemoteNotifs()))
  })
}