package com.isaackhor.hermes.views.viewAddNotif

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.isaackhor.hermes.R
import com.isaackhor.hermes.source.NotifsRepo
import com.isaackhor.hermes.utils.Utils

class AddNotifActivity : AppCompatActivity() {
  private lateinit var editTitle: EditText
  private lateinit var editContent: EditText

  private lateinit var viewModel: AddNotifViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_notif)

    editTitle = findViewById(R.id.add_edit_title)
    editContent = findViewById(R.id.add_edit_content)

    setSupportActionBar(findViewById(R.id.add_toolbar))
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      setDisplayShowHomeEnabled(true)
    }

    // ViewModel
    viewModel = ViewModelProviders.of(this).get(AddNotifViewModel::class.java)
    viewModel.setRepo(NotifsRepo.TESTING)

    Utils.onEditTextChanged(editTitle) { s -> viewModel.title.value = s }
    Utils.onEditTextChanged(editContent) { s -> viewModel.content.value = s }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.add_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_add_confirm -> {
//        viewModel.addNewNotif()
        finish()
        true
      }
      android.R.id.home -> {
        finish()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}