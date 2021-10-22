package com.phone.company
package services

import model.Register

trait RegisterService[F[_]] {
  def read(): F[Seq[Register]]
  def write(register: Seq[Register]): F[Unit]
}
