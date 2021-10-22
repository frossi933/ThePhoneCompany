package com.phone.company
package model

import utils.DurationUtils

import java.time.Duration

class DurationRange(from: Duration, to: Duration) {
  // inclusive
  def contains(duration: Duration): Boolean = duration.compareTo(from) >= 0 && duration.compareTo(to) <= 0
}

object DurationRange {

  def make(from: Duration, to: Duration) = new DurationRange(from, to)

  def make(from: String, to: String) = new DurationRange(DurationUtils.fromString(from), DurationUtils.fromString(to))
}
