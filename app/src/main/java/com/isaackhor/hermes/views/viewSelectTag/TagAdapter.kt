package com.isaackhor.hermes.views.viewSelectTag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.isaackhor.hermes.model.NotifTag

class TagAdapter constructor(
  context: Context,
  objects: MutableList<NotifTag> = mutableListOf()
) : ArrayAdapter<NotifTag>(context, android.R.layout.simple_list_item_multiple_choice, objects) {
  private val inflater = LayoutInflater.from(context)

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view = (convertView ?:
      inflater.inflate(android.R.layout.simple_list_item_multiple_choice,
        parent, false)) as TextView

    val item = getItem(position)
    view.text = item.name

    return view
  }
}