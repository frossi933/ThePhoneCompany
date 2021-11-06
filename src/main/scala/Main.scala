package com.phone.company

import cats.effect.{IO, IOApp}
import cats.implicits._
import cats.data.ReaderT
import model.Register.showRegisterTotalCost
import services.components.Services.{promotionsService, registerService}


object Main extends IOApp.Simple {

  val program: ReaderT[IO, Env, Unit] = for {
    registerService <- registerService
    promotionsService <- promotionsService
    registers <- registerService.getRegisters()
    registersUpdated <- registers.map(promotionsService.applyPromotions).sequence
    prog <- ReaderT.liftF(registersUpdated.map(IO.println(_)(showRegisterTotalCost)).sequence.void)
  } yield prog

  val run = program.run(LiveEnv)

}
