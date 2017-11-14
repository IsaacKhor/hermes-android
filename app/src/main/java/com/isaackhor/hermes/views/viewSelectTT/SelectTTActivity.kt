package com.isaackhor.hermes.views.viewSelectTT

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.isaackhor.hermes.R
import com.isaackhor.hermes.utils.getViewModel
import com.isaackhor.hermes.utils.observe
import kotlinx.android.synthetic.main.activity_select_topic_target.*

class SelectTTActivity : AppCompatActivity() {
  private lateinit var adapter: TTAdapter
  private lateinit var vm: SelectTTViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_select_topic_target)
    setSupportActionBar(select_tt_toolbar)

    vm = getViewModel(SelectTTViewModel::class.java)
    val mode = intent.getStringExtra(MODE_ARGKEY)
    vm.setNewMode(mode)

    adapter = TTAdapter(vm)
    stt_listTargets.adapter = adapter
    stt_listTargets.layoutManager = LinearLayoutManager(this)

    vm.groups.observe(this, { adapter.groups = it ?: emptyList() })
    adapter.groups = vm.groups.value ?: emptyList()
  }

  companion object {
    val MODE_ARGKEY = "tt_mode"
    val MODE_TARGET = SelectTTViewModel.MODE_TARGET
    val MODE_TOPIC = SelectTTViewModel.MODE_TOPIC
  }
}