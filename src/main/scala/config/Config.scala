package com.phone.company
package config

import model.DurationRange

import java.time.Duration

object Config {

  val REGISTER_FILENAME = "src/main/resources/calls.txt"

  val costsPerDuration: Map[DurationRange, BigDecimal] = Map(
    DurationRange.make(Duration.ZERO, Duration.ofMinutes(3)) -> BigDecimal.valueOf(0.05),
    DurationRange.make(Duration.ofMinutes(3), Duration.ofHours(24)) -> BigDecimal.valueOf(0.03)
  )

}
