package com.isaackhor.hermes.views.viewNotifDetail

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.isaackhor.hermes.R
import com.isaackhor.hermes.utils.getViewModel
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

    viewModel = getViewModel(NotifDetailViewModel::class.java)
    viewModel.setId(id)
    viewModel.notif.observe(this, Observer {
      it?.let {
        dt_textTitle.text = it.title
        dt_textContent.text = it.content
      }
    })
    viewModel.tags.observe(this, Observer {
      it?.let {
        if (it.isEmpty())
          dt_editTags.setText(R.string.no_tags)
        else
          dt_editTags.text = it.map { it.name }.reduceRight { a, b -> a + '\n' + b }
      }
    })
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
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