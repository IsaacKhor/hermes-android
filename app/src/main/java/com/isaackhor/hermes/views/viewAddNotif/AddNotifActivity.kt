package com.isaackhor.hermes.views.viewAddNotif

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.isaackhor.hermes.R
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.utils.*
import com.isaackhor.hermes.views.viewSelectTag.SelectTagActivity
import kotlinx.android.synthetic.main.activity_add_notif.*

class AddNotifActivity : AppCompatActivity() {
  private lateinit var viewModel: AddNotifViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_notif)

    setSupportActionBar(add_toolbar)
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      setDisplayShowHomeEnabled(true)
    }

    // ViewModel
    viewModel = getViewModel(AddNotifViewModel::class.java)
    with(viewModel) {
      onEditTextChanged(add_editTitle) { s -> title.value = s }
      onEditTextChanged(add_editContent) { s -> content.value = s }

      // Show a 'click here to add more' msg when targets/tags is blank
      obsListToStr(tags, R.string.label_empty_tt_list,
        { it.name }, { add_editTags.text = it })

      // Force the event to fire on load
      tags.value = listOf()
    }

    viewModel.onNotifSendSuccess.observe(this) { it?.let { onNotifAdded(it) } }

    // Show detail selection activity on click
    add_editTags.setOnClickListener { selectTags() }

    // Snackbar
    // This snackbar is used for the 'sending notif to server msg'
    setupStatusSnackbar(this, viewModel.isSendingNotif,
      R.string.sending_notif_msg)

    // This snackbar is used for all other messages
    setupSnackbar(this, viewModel.snackbarMsg, Snackbar.LENGTH_LONG)
  }

  private fun onNotifAdded(notif: Notif) {
    val intent = Intent()
    intent.putExtra(NEW_NOTIF_ID, notif.id)
    setResult(Activity.RESULT_OK, intent)
    finish()
  }

  private fun selectTags() {
    val intent = Intent(this, SelectTagActivity::class.java)
    startActivityForResult(intent, 0)
  }

  /*
   * Parses information from SelectTagActivity
   */
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (resultCode == Activity.RESULT_CANCELED || data == null) return
    val res = data.getIntArrayExtra(SelectTagActivity.RESULT_IDS).toList()
    viewModel.setNewTagsFromId(res)
  }

  private fun <T> obsListToStr(list: LiveData<List<T>>, emptyStrId: Int,
                               f: (T) -> String, g: (String?) -> Unit) {
    list.observe(this, Observer { l ->
      val s = l?.let {
        if (it.isEmpty()) getString(emptyStrId)
        else it.map(f).reduceRight { a, b -> a + '\n' + b }
      }
      g(s)
    })
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_add, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.menu_add_confirm -> {
      viewModel.addNewNotif()
      true
    }
    android.R.id.home -> {
      finish()
      true
    }
    else -> super.onOptionsItemSelected(item)
  }

  companion object {
    val NEW_NOTIF_ID = "new_notif_id"
  }
}