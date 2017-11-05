package com.isaackhor.hermes.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

object Utils {
  fun onEditTextChanged(view: EditText, f: (String) -> Unit) {
    view.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        f(s.toString())
      }

      override fun afterTextChanged(s: Editable?) {}
    })
  }
}