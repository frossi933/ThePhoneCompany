package com.phone.company
package services

import model.Commons.Duration
import cats.data.ReaderT


trait CostsService[F[_]] {

  def calculateUnitCost(duration: Duration): ReaderT[F, Env, BigDecimal]

}
