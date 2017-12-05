package com.isaackhor.hermes.views.viewSelectTag

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.isaackhor.hermes.R
import com.isaackhor.hermes.utils.addBackBtn
import com.isaackhor.hermes.utils.getViewModel
import com.isaackhor.hermes.utils.observe
import kotlinx.android.synthetic.main.activity_select_tag.*

class SelectTagActivity : AppCompatActivity() {
  private lateinit var listAdapter: TagAdapter
  private lateinit var vm: SelectTagVM

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_select_tag)

    setSupportActionBar(select_tt_toolbar)
    supportActionBar?.addBackBtn()

    // Tell VM to retrieve data for the correct mode
    vm = getViewModel(SelectTagVM::class.java)

    // Set up list adapter
    listAdapter = TagAdapter(this, mutableListOf())
    vm.tags.observe(this) { it?.let { listAdapter.addAll(*it.toTypedArray()) } }

    stt_listTags.apply {
      adapter = listAdapter
      choiceMode = ListView.CHOICE_MODE_MULTIPLE
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_select_tt, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
    R.id.menu_select_tt_confirm -> retOptions()
    android.R.id.home -> retOptions()
    else -> super.onOptionsItemSelected(item)
  }

  private fun retOptions(): Boolean {
    val res = stt_listTags.checkedItemPositions

    // Convert SparseBooleanArray to a useful data structure
    val selected = (0 until res.size())
      .filter { res.valueAt(it) }
      .map { res.keyAt(it) }
      .map { listAdapter.getItem(it) }
      .map { it.id }

    if (selected.isEmpty()) {
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
    val RESULT_IDS = "result_ids"
  }
}