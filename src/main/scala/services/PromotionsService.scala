package com.phone.company
package services

import model.Commons.Promotion
import model.Customer

trait PromotionsService[F[_]] {

  def getByCustomer(customer: Customer): F[List[Promotion]]

}
