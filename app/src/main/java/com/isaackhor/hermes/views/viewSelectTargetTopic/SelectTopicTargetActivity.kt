package com.isaackhor.hermes.views.viewSelectTargetTopic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListAdapter
import android.widget.ListView
import com.isaackhor.hermes.R

class SelectTopicTargetActivity : AppCompatActivity() {
  private lateinit var list: ListView
  private lateinit var adapter: ListAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_select_topic_target)
  }
}