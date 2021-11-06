package com.phone.company
package repositories.components

import repositories.CallsRepository

import cats.effect.IO


trait CallsRepositoryComponent {
  def callsRepository: CallsRepository[IO]
}
