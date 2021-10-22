package com.phone.company
package utils

import java.time.{Duration, LocalTime}

object DurationUtils {

  def fromString(durationStr: String): Duration = Duration.between(LocalTime.MIN, LocalTime.parse(durationStr))

}
