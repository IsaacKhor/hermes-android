package com.isaackhor.hermes.source

import com.isaackhor.hermes.model.Notif
import nl.komponents.kovenant.Promise

interface DataSource {
  fun getNotifs(): Promise<List<Notif>, Exception>
  fun getNotif(id: Int): Promise<Notif?, Exception>
  fun addNotif(notif: Notif) : Promise<Notif, Exception>
  fun fetchNotifs(): Promise<List<Notif>, Exception>
}