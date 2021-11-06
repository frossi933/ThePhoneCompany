package com.phone.company
package repositories.components

import repositories.PromotionsRepository

import cats.effect.IO


trait PromotionsRepositoryComponent {
  def promotionsRepository: PromotionsRepository[IO]
}
