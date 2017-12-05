package com.isaackhor.hermes.views.viewNotifs

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.isaackhor.hermes.R
import com.isaackhor.hermes.model.Notif
import kotlinx.collections.immutable.ImmutableList

class NotifsAdapter(
  private var notifs: ImmutableList<Notif>,
  private var viewModel: NotifsViewModel
) : RecyclerView.Adapter<NotifsAdapter.NotifViewHolder>() {
  override fun onBindViewHolder(holder: NotifViewHolder, position: Int) =
    holder.bind(notifs[position].title)

  override fun getItemCount(): Int = notifs.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
    val context = parent.context
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(R.layout.view_notif, parent, false)
    return NotifViewHolder(view,
      { pos -> viewModel.openNotifDetails(notifs[pos].id) })
  }

  fun updateNotifsList(to: ImmutableList<Notif>) {
    notifs = to
    notifyDataSetChanged()
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

    fun bind(title: String) {
      notifNameView.text = title
    }
  }
}