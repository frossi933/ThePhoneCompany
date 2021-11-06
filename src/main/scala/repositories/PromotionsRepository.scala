package com.phone.company
package repositories

import model.Commons.Promotion
import model.Customer


trait PromotionsRepository[F[_]] {

  def getByCustomer(customer: Customer): F[List[Promotion]]

}
