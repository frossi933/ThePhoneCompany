package com.phone.company

import cats.effect.{IO, IOApp}
import cats.implicits._
import services.{CustomPromotionsService, FileRegisterService}
import model.Register

import scala.language.postfixOps

object Main extends IOApp.Simple {

  val registerService = new FileRegisterService()
  val promotionsService = new CustomPromotionsService()

  private def applyPromotions(register: Register): IO[Register] = {
    IO.println(s"Getting promotions for customer: ${register.customer}") *>
    promotionsService.getByCustomer(register.customer).map(promotions => {
      promotions.foldLeft(register) { case(reg, promotion) => promotion(reg) }
    })
  }

  import model.Register.showRegister

  val run = {
    IO.println("Reading calls from register...") *> {
      registerService.read()
        .flatMap(registers => registers.map(applyPromotions).sequence)
        .flatMap(registersUpdated => {
          IO.println("Promotions applied. Results:") *>
            registersUpdated.map(a => IO.println(a)).sequence.void
        })
    }
  }
}
