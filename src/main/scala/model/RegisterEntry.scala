package com.phone.company
package model

import model.Commons.{Duration, FORMAT_STRING_SEPARATOR, PhoneNumber}

import cats.Show


case class RegisterEntry(phoneNumber: PhoneNumber, duration: Duration, cost: BigDecimal)

object RegisterEntry {

  implicit val showRegEntry: Show[RegisterEntry] = Show.show(entry => List(entry.phoneNumber, entry.duration, entry.cost).mkString(FORMAT_STRING_SEPARATOR))

}

