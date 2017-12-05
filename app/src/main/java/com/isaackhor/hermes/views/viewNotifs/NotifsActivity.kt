package com.isaackhor.hermes.views.viewNotifs

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.isaackhor.hermes.R
import com.isaackhor.hermes.utils.getViewModel
import com.isaackhor.hermes.views.viewAddNotif.AddNotifActivity
import com.isaackhor.hermes.views.viewNotifDetail.NotifDetailActivity
import kotlinx.android.synthetic.main.activity_notifications.*
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.toImmutableList

class NotifsActivity : AppCompatActivity(), LifecycleOwner {
  private lateinit var viewModel: NotifsViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notifications)

    viewModel = getViewModel(NotifsViewModel::class.java)
    viewModel.loadNotifs(true, true)

    // User clicked on a notification in the list, switch to details activity
    viewModel.openNotifDetailsEvent.observe(this, Observer { id -> showNotifDetails(id) })
    viewModel.newNotifEvent.observe(this, Observer { showAddNotifUi() })

    // Recycler view
    nt_notifsList.setHasFixedSize(true)
    nt_notifsList.layoutManager = LinearLayoutManager(this)
    val adapter = NotifsAdapter(immutableListOf(), viewModel)
    nt_notifsList.adapter = adapter

    viewModel.notifs.observe(this,
      Observer { new ->
        if (new != null)
          adapter.updateNotifsList(new.toImmutableList())
      })

    // Swipe refresh view
    nt_swipeRefresh.setOnRefreshListener { viewModel.loadNotifs(true, true) }

    viewModel.dataLoading.observe(this,
      Observer { isLoading ->
        if (isLoading != null)
          nt_swipeRefresh.isRefreshing = isLoading
      })

    // Toolbar
    setSupportActionBar(findViewById(R.id.main_toolbar))
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_notifs, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_add_notif -> viewModel.addNewNotif()
      R.id.menu_select_notifs_filter -> viewModel.modifyFilter()
      else -> super.onOptionsItemSelected(item)
    }
    return true
  }

  private fun showNotifDetails(notifId: Int?) {
    if (notifId == null) {
      Log.w("NotifsActivity", "Trying to show notif without id; this shouldn't happen")
      return
    }
    val intent = Intent(this, NotifDetailActivity::class.java)
    intent.putExtra(NotifDetailActivity.NOTIF_ID_DETAIL, notifId)
    startActivityForResult(intent, 0)
  }

  private fun showAddNotifUi() {
    val intent = Intent(this, AddNotifActivity::class.java)
    startActivity(intent)
  }
}
