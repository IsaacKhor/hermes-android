package com.isaackhor.hermes.model.db

import io.reactivex.Single

class RemoteNotifs {
  var id = 100

  fun fetch(): Single<Unit> = Single.just(Unit)
}