package com.phone.company
package model

import model.Commons.{Duration, PhoneNumber, FORMAT_STRING_SEPARATOR}

import cats.Show

case class Call(customer: Customer, phoneNumber: PhoneNumber, duration: Duration)

object Call {
  implicit val showCall: Show[Call] = Show.show(call => Seq(call.customer, call.phoneNumber, call.duration).mkString(FORMAT_STRING_SEPARATOR))
}
