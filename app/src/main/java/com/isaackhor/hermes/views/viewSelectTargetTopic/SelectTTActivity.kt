package com.isaackhor.hermes.views.viewSelectTargetTopic

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import com.isaackhor.hermes.R
import com.isaackhor.hermes.VMFactory
import com.isaackhor.hermes.model.NotifGroup
import com.isaackhor.hermes.utils.getViewModel

class SelectTTActivity : AppCompatActivity() {
  private lateinit var list: ListView
  private lateinit var adapter: ArrayAdapter<NotifGroup>
  private lateinit var vm: SelectTTViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_select_topic_target)
    vm = getViewModel(SelectTTViewModel::class.java)

    val mode = intent.getStringExtra(MODE_ARGKEY)
    vm.setNewMode(mode)

    adapter = ArrayAdapter<NotifGroup>(this,
        android.R.layout.simple_list_item_multiple_choice, vm.groups.value)

    vm.groups.observe(this, Observer { e ->
      e?.let {
        adapter.clear()
        adapter.addAll(*e.toTypedArray())
        adapter.notifyDataSetInvalidated()
      }
    })
  }

  companion object {
    val MODE_ARGKEY = "tt_mode"
    val MODE_TARGET = SelectTTViewModel.MODE_TARGET
    val MODE_TOPIC = SelectTTViewModel.MODE_TOPIC
  }
}