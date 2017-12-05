package com.isaackhor.hermes.views.viewNotifs

import android.arch.lifecycle.LifecycleOwner
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.isaackhor.hermes.R
import com.isaackhor.hermes.utils.getViewModel
import com.isaackhor.hermes.utils.observe
import com.isaackhor.hermes.utils.setupSnackbar
import com.isaackhor.hermes.views.viewAddNotif.AddNotifActivity
import com.isaackhor.hermes.views.viewNotifDetail.NotifDetailActivity
import kotlinx.android.synthetic.main.activity_notifications.*

class NotifsActivity : AppCompatActivity(), LifecycleOwner {
  private lateinit var vm: NotifsViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notifications)

    vm = getViewModel(NotifsViewModel::class.java)
    vm.loadNotifs(true, false)

    // User clicked on a notification in the list, switch to details activity
    vm.openNotifDetailsEvent.observe(this, ::showNotifDetails)
    vm.newNotifEvent.observe(this) { showAddNotifUi() }

    // Recycler view
    nt_notifsList.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(this@NotifsActivity)

      val adap = NotifsAdapter(vm)
      vm.notifs.observe(this@NotifsActivity, adap::setList)
      adapter = adap
    }

    // Swipe refresh view
    nt_swipeRefresh.setOnRefreshListener() { vm.loadNotifs(true, true) }
    vm.dataLoading.observe(this) {
      it?.let { nt_swipeRefresh.isRefreshing = it }
    }

    // Snackbar
    setupSnackbar(this, vm.snackbar, Snackbar.LENGTH_SHORT)

    // Toolbar
    setSupportActionBar(findViewById(R.id.main_toolbar))
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_notifs, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_add_notif -> vm.addNewNotif()
      R.id.menu_select_notifs_filter -> vm.modifyFilter()
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
