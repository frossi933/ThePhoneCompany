package com.phone.company
package services.components

import services.CostsService

import cats.effect.IO


trait CostsServiceComponent {
  def costsService: CostsService[IO]
}
