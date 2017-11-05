package com.isaackhor.hermes.viewNotifs

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.isaackhor.hermes.viewAddNotif.AddNotifActivity
import com.isaackhor.hermes.R
import com.isaackhor.hermes.model.Notif
import com.isaackhor.hermes.source.NotifsRepo
import com.isaackhor.hermes.source.TestingRepo
import com.isaackhor.hermes.viewNotifDetail.NotifDetailActivity
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.toImmutableList

class NotifsActivity : AppCompatActivity(), LifecycleOwner {
  private lateinit var viewModel: NotifsViewModel
  private lateinit var recyclerView: RecyclerView
  private lateinit var addNotifFab: FloatingActionButton

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notifications)

    viewModel = ViewModelProviders.of(this).get(NotifsViewModel::class.java)
    viewModel.setRepo(NotifsRepo.TESTING)
    viewModel.loadNotifs(true, true)

    // User clicked on a notification in the list, switch to details activity
    viewModel.openNotifDetailsEvent.observe(this, Observer { id -> showNotifDetails(id) })
    viewModel.newNotifEvent.observe(this, Observer { showAddNotifUi() })

    // Recycler view
    recyclerView = findViewById(R.id.notifsList)
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = LinearLayoutManager(this)
    val adapter = NotifsAdapter(immutableListOf(Notif.TEST_NOTIF), viewModel)
    recyclerView.adapter = adapter

    viewModel.notifs.observe(this,
        Observer { new -> if (new != null) adapter.updateNotifsList(new.toImmutableList()) })

    // Add notif button
    addNotifFab = findViewById(R.id.addNotifFab)
    addNotifFab.setOnClickListener { viewModel.addNewNotif() }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.notifs_menu, menu)
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
    if(notifId == null) {
      Log.w("NotifsActivity", "Trying to show notif without id; this shouldn't happen")
      return
    }
    val intent = Intent(this, NotifDetailActivity::class.java)
    intent.putExtra(NotifDetailActivity.NOTIF_ID_DETAIL, notifId)
    startActivity(intent)
  }

  private fun showAddNotifUi() {
    val intent = Intent(this, AddNotifActivity::class.java)
    startActivity(intent)
  }
}
