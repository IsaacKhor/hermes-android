package com.isaackhor.hermes.views.viewSelectTargetTopic

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.isaackhor.hermes.R
import com.isaackhor.hermes.model.NotifGroup
import com.isaackhor.hermes.source.NotifsRepo
import com.isaackhor.hermes.utils.inflate
import kotlinx.android.synthetic.main.view_group.view.*

class TTAdapter(
    private val viewModel: SelectTTViewModel
) : RecyclerView.Adapter<TTAdapter.Holder>() {
  var groups = emptyList<NotifGroup>()
    set(new) {
      field = new
      notifyDataSetChanged()
    }

  override fun getItemCount(): Int = groups.size

  override fun onBindViewHolder(holder: Holder, position: Int) {
    holder.itemView.view_group_title.text = groups[position].name
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      Holder(parent.inflate(R.layout.view_group))

  class Holder(view: View) : RecyclerView.ViewHolder(view)
}