package com.phone.company
package services.components

import services.RegisterService

import cats.effect.IO


trait RegisterServiceComponent {
  def registerService: RegisterService[IO]
}