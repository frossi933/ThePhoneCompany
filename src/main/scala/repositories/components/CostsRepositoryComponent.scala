package com.phone.company
package repositories.components

import repositories.CostsRepository

import cats.effect.IO


trait CostsRepositoryComponent {
  def costsRepository: CostsRepository[IO]
}
