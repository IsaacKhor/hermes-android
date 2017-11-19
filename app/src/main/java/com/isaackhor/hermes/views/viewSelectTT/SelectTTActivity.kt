package com.isaackhor.hermes.views.viewSelectTT

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import com.isaackhor.hermes.R
import com.isaackhor.hermes.model.NotifGroup
import com.isaackhor.hermes.utils.getViewModel
import com.isaackhor.hermes.utils.observe
import com.isaackhor.hermes.utils.addBackBtn
import kotlinx.android.synthetic.main.activity_select_tt.*

class SelectTTActivity : AppCompatActivity() {
  private lateinit var listAdapter: TTAdapter
  private lateinit var vm: SelectTTViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_select_tt)

    setSupportActionBar(select_tt_toolbar)
    supportActionBar?.addBackBtn()

    // Tell VM to retrieve data for the correct mode
    vm = getViewModel(SelectTTViewModel::class.java)
    val mode = intent.getStringExtra(MODE_ARGKEY)
    vm.setNewMode(mode)

    listAdapter = TTAdapter(this,
        vm.groups.value?.toMutableList() ?: mutableListOf())

    stt_listTargets.apply {
      adapter = listAdapter
      choiceMode = ListView.CHOICE_MODE_MULTIPLE
    }

    vm.groups.observe(this)
        { it?.let { listAdapter.addAll(*it.toTypedArray()) }}
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_select_tt, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
      R.id.menu_select_tt_confirm -> retOptions()
      android.R.id.home -> retOptions()
      else -> super.onOptionsItemSelected(item)
    }

  private fun retOptions(): Boolean {
    val res = stt_listTargets.checkedItemPositions

    // Convert SparseBooleanArray to a useful data structure
    val selected = (0 until res.size())
        .filter { res.valueAt(it) }
        .map { res.keyAt(it) }
        .map { listAdapter.getItem(it) }
        .map { it.id }

    if(selected.isEmpty()) {
      setResult(Activity.RESULT_CANCELED)
    } else {
      val intent = Intent()
      intent.putExtra(RESULT_IDS, selected.toIntArray())
      setResult(Activity.RESULT_OK, intent)
    }

    finish()
    return true
  }

  companion object {
    val MODE_ARGKEY = "tt_mode"
    val MODE_TARGET = SelectTTViewModel.MODE_TARGET
    val MODE_TOPIC = SelectTTViewModel.MODE_TOPIC

    val RESULT_IDS = "result_ids"
  }
}