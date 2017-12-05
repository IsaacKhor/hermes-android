package com.isaackhor.hermes.views.viewNotifs

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.isaackhor.hermes.R
import com.isaackhor.hermes.model.Notif

class NotifsAdapter(
  private var viewModel: NotifsViewModel
) : PagedListAdapter<Notif, NotifsAdapter.NotifViewHolder>(DIFF_CALLBACK) {

  override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
    getItem(position)?.let {
      holder.bindTo(it)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(R.layout.view_notif, parent, false)
    return NotifViewHolder(view) { viewModel.openNotifDetails(getItem(it)!!.id) }
  }

  class NotifViewHolder(
    view: View,
    private val onClickListener: (Int) -> Unit
  ) : RecyclerView.ViewHolder(view), View.OnClickListener {

    private val notifNameView: TextView = view.findViewById(R.id.notif_row_title)

    init {
      notifNameView.setOnClickListener(this)
    }

    override fun onClick(v: View) = onClickListener(adapterPosition)

    fun bindTo(notif: Notif) {
      notifNameView.text = notif.title
    }
  }

  companion object {
    val DIFF_CALLBACK = object : DiffCallback<Notif>() {
      override fun areItemsTheSame(oldItem: Notif, newItem: Notif) = oldItem.id == newItem.id
      override fun areContentsTheSame(oldItem: Notif, newItem: Notif) = oldItem == newItem
    }
  }
}