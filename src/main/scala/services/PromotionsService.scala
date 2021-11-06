package com.phone.company
package services

import model.Register

import cats.data.ReaderT
import cats.effect.IO

trait PromotionsService[F[_]] {

  def applyPromotions(reg: Register): ReaderT[IO, Env, Register]

}
