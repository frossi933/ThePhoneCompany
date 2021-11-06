package com.phone.company
package model

import utils.DurationUtils
import java.time.Duration


class DurationRange(val from: Duration,val to: Duration) {
  /**
   * @param duration value to check if it's contained
   * @return true if duration is contained in the range including both limits
   */
  def contains(duration: Duration): Boolean = duration.compareTo(from) >= 0 && duration.compareTo(to) <= 0
}

object DurationRange {

  def make(from: Duration, to: Duration) = new DurationRange(from, to)

  def make(from: String, to: String) = for {
    durationFrom <- DurationUtils.fromString(from)
    durationTo <- DurationUtils.fromString(to)
  } yield new DurationRange(durationFrom, durationTo)

}
