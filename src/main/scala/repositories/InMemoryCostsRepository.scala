package com.phone.company
package repositories

import model.DurationRange

import cats.effect.IO

import java.time.Duration


object InMemoryCostsRepository extends CostsRepository[IO] {

  private val fromZeroTo3Mins: DurationRange = DurationRange.make(Duration.ZERO, Duration.ofMinutes(3))
  private val from3MinsAnd1SecTo24Hours: DurationRange = DurationRange.make(Duration.ofMinutes(3).plus(Duration.ofSeconds(1)), Duration.ofHours(24))

  override def getCostPerSecondByDuration(duration: Duration): IO[Option[BigDecimal]] = duration match {
    case d if fromZeroTo3Mins.contains(d) => IO.pure(Some(BigDecimal.valueOf(0.05)))
    case d if from3MinsAnd1SecTo24Hours.contains(d) => IO.pure(Some(BigDecimal.valueOf(0.03)))
    case _ => IO.pure(None)
  }

}
