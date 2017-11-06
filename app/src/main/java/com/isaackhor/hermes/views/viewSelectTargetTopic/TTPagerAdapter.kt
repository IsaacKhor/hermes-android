package com.isaackhor.hermes.views.viewSelectTargetTopic

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log


class TTPagerAdapter(private val fm: FragmentManager) : FragmentPagerAdapter(fm) {
  override fun getItem(position: Int): Fragment? {
    return when (position) {
      TARGET_POS -> TTFrag.newInstance(TTFrag.SelectType.TARGET)
      TOPIC_POS -> TTFrag.newInstance(TTFrag.SelectType.TOPIC)
      else -> {
        Log.e("TTPageAdapter", "Invalid position $position")
//        throw UnsupportedOperationException("Invalid position $position")
        return null
      }
    }
  }

  override fun getCount(): Int = 2

  companion object {
    val TARGET_POS = 1
    val TOPIC_POS = 2
  }
}