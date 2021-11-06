package com.phone.company
package services

import model.Register

import cats.effect.IO
import cats.data.ReaderT
import repositories.components.Repositories.promotionsRepository


object PromotionsServiceImpl extends PromotionsService[IO] {

  override def applyPromotions(reg: Register): ReaderT[IO, Env, Register] = promotionsRepository.flatMap { promotionsRepository =>
    ReaderT.liftF(
      promotionsRepository.getByCustomer(reg.customer).map(_.foldLeft(reg){ case(reg, promotion) => promotion(reg) })
    )
  }

}
