package com.isaackhor.hermes.utils

import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.bluetooth.le.BluetoothLeScanner
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

fun onEditTextChanged(view: EditText, f: (String) -> Unit) {
  view.addTextChangedListener(object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
        f(s.toString())
    override fun afterTextChanged(s: Editable?) {}
  })
}

fun Activity.getRootView(): View {
  return findViewById(android.R.id.content)
}

fun Activity.setupSnackbar(lifecycleOwner: LifecycleOwner,
                           snackbarLiveEvent: SingleLiveEvent<Int>, timeLength: Int) {
  snackbarLiveEvent.observe(lifecycleOwner, Observer {
    it?.let { getRootView().showSnackbar(getString(it), timeLength) }
  })
}

fun Activity.setupStrSnackbar(lifecycleOwner: LifecycleOwner,
                              snackbarLiveEvent: SingleLiveEvent<String>, timeLength: Int) {
  snackbarLiveEvent.observe(lifecycleOwner, Observer {
    it?.let { getRootView().showSnackbar(it, timeLength) }
  })
}

fun View.showSnackbar(text: String, timeLength: Int) {
  Snackbar.make(this, text, timeLength).show()
}