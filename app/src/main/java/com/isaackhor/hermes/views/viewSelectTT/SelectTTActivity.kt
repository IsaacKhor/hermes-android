package com.isaackhor.hermes.views.viewSelectTT

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.isaackhor.hermes.R
import com.isaackhor.hermes.model.NotifGroup
import com.isaackhor.hermes.utils.getViewModel
import com.isaackhor.hermes.utils.observe
import kotlinx.android.synthetic.main.activity_select_tt.*

class SelectTTActivity : AppCompatActivity() {
  private lateinit var listAdapter: ArrayAdapter<String>
  private lateinit var vm: SelectTTViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_select_tt)
    setSupportActionBar(select_tt_toolbar)

    vm = getViewModel(SelectTTViewModel::class.java)
    val mode = intent.getStringExtra(MODE_ARGKEY)
    vm.setNewMode(mode)

    listAdapter = ArrayAdapter(this,
        android.R.layout.simple_list_item_multiple_choice,
        vm.groupsName.value?.toMutableList() ?: mutableListOf())

    stt_listTargets.apply {
      adapter = listAdapter
      choiceMode = ListView.CHOICE_MODE_MULTIPLE
    }

    vm.groupsName.observe(this,
        Observer { it?.let { listAdapter.addAll(*it.toTypedArray()) }})
  }

  companion object {
    val MODE_ARGKEY = "tt_mode"
    val MODE_TARGET = SelectTTViewModel.MODE_TARGET
    val MODE_TOPIC = SelectTTViewModel.MODE_TOPIC
  }
}