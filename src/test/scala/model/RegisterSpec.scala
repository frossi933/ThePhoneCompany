package com.phone.company
package model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.time.Duration

class RegisterSpec extends AnyFlatSpec with Matchers {

  "Register.totalCost" should "sum all costs of its entries" in {
    val customer = Customer("CustomerA")
    val phoneNumber1 = "111-111-111"
    val costs = (1 to 10).map(BigDecimal.valueOf(_))
    val entries: Seq[RegisterEntry] = costs.map(RegisterEntry(phoneNumber1, Duration.ZERO, _))
    val register = Register(customer, entries)

    register.totalCost shouldEqual costs.sum
  }
}
