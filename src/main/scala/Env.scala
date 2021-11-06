package com.phone.company

import cats.data.ReaderT
import cats.effect.IO
import config.ConfigComponent
import services.components.ServicesComponent
import repositories.components.RepositoriesComponent


trait Env extends ConfigComponent with ServicesComponent with RepositoriesComponent

object Env {

  val env = ReaderT.ask[IO, Env]

  val config = env.map(_.config)
  val services = env.map(_.services)
  val repositories = env.map(_.repositories)

}
