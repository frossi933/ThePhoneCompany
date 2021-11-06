package com.phone.company
package services

import model.Commons.{Duration, MisconfigurationException}

import cats.data.ReaderT
import cats.effect.IO
import repositories.components.Repositories.costsRepository


object CostsServiceImpl extends CostsService[IO] {

  override def calculateUnitCost(duration: Duration): ReaderT[IO, Env, BigDecimal] = {
    costsRepository.flatMap { costsRepository =>
      ReaderT.liftF(costsRepository.getCostPerSecondByDuration(duration).flatMap(_ match {
        case Some(costPerSecond) => IO.pure(costPerSecond * BigDecimal.valueOf(duration.toSeconds))
        case None => IO.raiseError(MisconfigurationException("call duration out of configured ranges"))
      }))
    }
  }
}
