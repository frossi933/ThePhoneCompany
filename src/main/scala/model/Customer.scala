package com.phone.company
package model

import cats.{Eq, Show}

case class Customer(name: String)

object Customer {

  implicit val showCustomer: Show[Customer] = Show.show(_.name)
  implicit val equalCustomer: Eq[Customer] = Eq.by(_.name)

}