package com.isaackhor.hermes.utils

import android.app.Activity
import android.arch.lifecycle.*
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.isaackhor.hermes.VMFactory

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

fun <T:ViewModel> AppCompatActivity.getViewModel(cls: Class<T>) =
    ViewModelProviders.of(this, VMFactory.getInstance(application)).get(cls)

fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachToRoot: Boolean = false): android.view.View {
  val inflater = android.view.LayoutInflater.from(context)
  return inflater.inflate(layoutId, this, attachToRoot)
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, f: (T?) -> Unit)
    = observe(owner, Observer { f(it) })

fun ActionBar.addBackBtn() {
  setDisplayHomeAsUpEnabled(true)
  setDisplayShowHomeEnabled(true)
}