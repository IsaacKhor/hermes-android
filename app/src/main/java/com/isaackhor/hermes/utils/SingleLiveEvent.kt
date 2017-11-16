package com.isaackhor.hermes.utils

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.util.Log
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Only notifies the observer after setValue has been called. Only one observer
 * will be notified
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
  private val pending = AtomicBoolean(false)

  override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
    if (hasActiveObservers())
      Log.w("SingleLiveEvent", "Multiple observers; only a single will be notified")

    super.observe(owner, Observer { t ->
      if (pending.compareAndSet(true, false))
        observer.onChanged(t)
    })
  }

  override fun setValue(value: T?) {
    pending.set(true)
    super.setValue(value)
  }

  override fun postValue(value: T?) {
    pending.set(true)
    super.postValue(value)
  }

  /* Used when T is void */
  fun call() { value = null }
}