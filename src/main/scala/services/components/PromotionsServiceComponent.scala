package com.phone.company
package services.components

import services.PromotionsService

import cats.effect.IO


trait PromotionsServiceComponent {
  def promotionsService: PromotionsService[IO]
}
