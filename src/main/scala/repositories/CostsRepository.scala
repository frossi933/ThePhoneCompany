package com.phone.company
package repositories

import java.time.Duration


trait CostsRepository[F[_]] {

  def getCostPerSecondByDuration(duration: Duration): F[Option[BigDecimal]]

}
