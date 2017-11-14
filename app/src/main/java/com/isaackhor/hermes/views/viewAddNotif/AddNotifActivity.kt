package com.isaackhor.hermes.views.viewAddNotif

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.isaackhor.hermes.R
import com.isaackhor.hermes.model.LimboNotif
import com.isaackhor.hermes.utils.*
import com.isaackhor.hermes.views.viewSelectTT.SelectTTActivity
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

      // Show a 'click here to add more' msg when targets/topics is blank
      obsListToStr(targets, R.string.label_empty_tt_list,
          { it.name }, { add_editTargets.text = it })
      obsListToStr(topics, R.string.label_empty_tt_list,
          { it.name }, { add_editTopics.text = it })

      // Force event to fire on load
      targets.value = listOf()
      topics.value = listOf()
    }

    viewModel.onNewNotifEvent.observe(this, Observer { ln -> returnRes(ln!!) })

    // Show detail selection activity on click
    add_editTargets.setOnClickListener { addTT(SelectTTActivity.MODE_TARGET) }
    add_editTopics.setOnClickListener { addTT(SelectTTActivity.MODE_TOPIC) }

    // Snackbar
    setupSnackbar(this, viewModel.snackbarMsg, Snackbar.LENGTH_LONG)
  }

  private fun addTT(mode: String) {
    val intent = Intent(this, SelectTTActivity::class.java)
    intent.putExtra(SelectTTActivity.MODE_ARGKEY, mode)
    startActivityForResult(intent, 0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
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

  private fun returnRes(notif: LimboNotif) {
    val ret = Intent()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.add_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
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
  }
}