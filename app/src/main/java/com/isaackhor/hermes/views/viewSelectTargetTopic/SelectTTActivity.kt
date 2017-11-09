package com.isaackhor.hermes.views.viewSelectTargetTopic

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListAdapter
import android.widget.ListView
import com.isaackhor.hermes.R
import com.isaackhor.hermes.VMFactory
import com.isaackhor.hermes.utils.getViewModel

class SelectTTActivity : AppCompatActivity() {
  private lateinit var list: ListView
  private lateinit var adapter: ListAdapter
  private lateinit var vm: SelectTTViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_select_topic_target)

    vm = getViewModel(SelectTTViewModel::class.java)
  }
}