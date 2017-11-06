package com.isaackhor.hermes.views.viewNotifDetail

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.isaackhor.hermes.R
import com.isaackhor.hermes.source.NotifsRepo
import kotlinx.android.synthetic.main.activity_notif_detail.*

class NotifDetailActivity : AppCompatActivity(), LifecycleOwner {
  private lateinit var viewModel: NotifDetailViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notif_detail)

    // Action bar
    val actionbar = findViewById<Toolbar>(R.id.detail_toolbar)
    setSupportActionBar(actionbar)
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      setDisplayShowHomeEnabled(true)
    }

    val id = intent.getIntExtra(NOTIF_ID_DETAIL, 0)

    viewModel = ViewModelProviders.of(this).get(NotifDetailViewModel::class.java)
    viewModel.setRepo(NotifsRepo.TESTING)
    viewModel.setId(id)
    viewModel.notif.observe(this, Observer { new ->
      if (new != null) {
        dt_textTitle.text = new.title
        dt_textContent.text = new.content
        dt_editTargets.text = new.targets.map { e -> e.name }.reduceRight { a, b -> a+'\n'+b}
        dt_editTopics.text = new.topics.map { e -> e.name }.reduceRight { a, b -> a+'\n'+b}
      }
    })
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when(item.itemId) {
      android.R.id.home -> {
        finish()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  companion object {
    val NOTIF_ID_DETAIL = "notif_id"
  }
}