package com.phone.company
package services

import model.Register
import cats.data.ReaderT


trait RegisterService[F[_]] {

  def getRegisters(): ReaderT[F, Env, Seq[Register]]

}
