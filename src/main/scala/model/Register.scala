package com.phone.company
package model

import cats.Show
import cats.implicits._


case class Register(customer: Customer, entries: Seq[RegisterEntry]) {

  def totalCost: BigDecimal = entries.map(_.cost).sum

}

object Register {

  import model.Customer.showCustomer

  implicit val showRegisterTotalCost: Show[Register] = Show.show(reg => s"${reg.customer.show}: ${reg.totalCost}")

}