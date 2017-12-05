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
import com.isaackhor.hermes.R
import java.util.concurrent.Executors

fun onEditTextChanged(view: EditText, f: (String) -> Unit) {
  view.addTextChangedListener(object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
      f(s.toString())

    override fun afterTextChanged(s: Editable?) {}
  })
}

fun Activity.getRootView(): View = findViewById(android.R.id.content)

fun Activity.setupSnackbar(lifecycleOwner: LifecycleOwner,
                           snackbarLiveEvent: LiveData<Int>, timeLength: Int) {
  snackbarLiveEvent.observe(lifecycleOwner, Observer {
    it?.let { getRootView().showSnackbar(getString(it), timeLength) }
  })
}

fun Activity.setupStatusSnackbar(lifecycleOwner: LifecycleOwner, shouldShow: LiveData<Boolean>,
                                 resId: Int) {
  setupStatusSnackbar(lifecycleOwner, shouldShow,
    MutableLiveData<Int>().also { it.value = resId })
}

fun Activity.setupStatusSnackbar(lifecycleOwner: LifecycleOwner, shouldShow: LiveData<Boolean>,
                                 snackbarLiveEvent: LiveData<Int>) {
  val bar =
    Snackbar.make(
      getRootView(),
      getString(snackbarLiveEvent.value ?: R.string.no_msg),
      Snackbar.LENGTH_INDEFINITE)

  shouldShow.observe(lifecycleOwner,
    Observer { it?.let { if (it) bar.show() else bar.dismiss() } })
  snackbarLiveEvent.observe(lifecycleOwner,
    Observer { it?.let { bar.setText(it) } })
}

fun Activity.setupStrSnackbar(lifecycleOwner: LifecycleOwner,
                              snackbarLiveEvent: LiveData<String>, timeLength: Int) {
  snackbarLiveEvent.observe(lifecycleOwner, Observer {
    it?.let { getRootView().showSnackbar(it, timeLength) }
  })
}

fun View.showSnackbar(text: String, timeLength: Int) {
  Snackbar.make(this, text, timeLength).show()
}

fun <T : ViewModel> AppCompatActivity.getViewModel(cls: Class<T>) =
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

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun ioThread(f: () -> Unit) {
  IO_EXECUTOR.execute(f)
}