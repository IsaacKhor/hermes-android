package com.isaackhor.hermes.utils

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.isaackhor.hermes.source.NotifsRepo
import com.isaackhor.hermes.views.viewAddNotif.AddNotifViewModel
import com.isaackhor.hermes.views.viewNotifDetail.NotifDetailViewModel
import com.isaackhor.hermes.views.viewNotifs.NotifsViewModel
import com.isaackhor.hermes.views.viewSelectTT.SelectTTViewModel

class VMFactory private constructor(
    private val repo: NotifsRepo
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return with(modelClass) {
      when {
        isAssignableFrom(AddNotifViewModel::class.java) -> AddNotifViewModel()
        isAssignableFrom(NotifDetailViewModel::class.java) -> NotifDetailViewModel(repo)
        isAssignableFrom(NotifsViewModel::class.java) -> NotifsViewModel(repo)
        isAssignableFrom(SelectTTViewModel::class.java) -> SelectTTViewModel(repo)
        else -> throw IllegalArgumentException("Unknown class: ${modelClass.name}")
      }
    } as T
  }

  companion object {
    private var INSTANCE: VMFactory? = null

    fun getInstance(application: Application) =
        INSTANCE ?: synchronized(VMFactory::class.java) {
          INSTANCE ?: VMFactory(NotifsRepo.TESTING).also { INSTANCE = it }
        }
  }
}