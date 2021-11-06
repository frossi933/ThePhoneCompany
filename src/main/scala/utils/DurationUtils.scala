package com.phone.company
package utils

import java.time.{Duration, LocalTime}
import scala.util.Try


object DurationUtils {

  def fromString(durationStr: String): Try[Duration] = Try {
    Duration.between(LocalTime.MIN, LocalTime.parse(durationStr))
  }

}
