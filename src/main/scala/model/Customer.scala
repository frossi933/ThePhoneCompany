package com.phone.company
package model

import cats.Show


case class Customer(name: String)

object Customer {

  implicit val showCustomer: Show[Customer] = Show.show(_.name)

}