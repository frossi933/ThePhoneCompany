package com.phone.company

import model.Commons.Promotion
import model.{Call, Customer, Register, RegisterEntry}

import java.time.Duration

trait Fixture {
  val customerA: Customer = Customer("CustomerA")
  val customerB: Customer = Customer("CustomerB")
  val phoneNumber1: String = "111-111-111"
  val phoneNumber2: String = "222-222-222"
  val phoneNumber3: String = "333-333-333"
  val durationZero: Duration = Duration.ZERO
  val duration1min: Duration = Duration.ofMinutes(1)
  val duration2min: Duration = Duration.ofMinutes(2)
  val duration3min: Duration = Duration.ofMinutes(3)
  val duration1Year: Duration = Duration.ofDays(365)
  val callsA = List(
    Call(customerA, phoneNumber1, duration1min),
    Call(customerA, phoneNumber2, duration2min),
    Call(customerA, phoneNumber3, duration3min)
  )
  val callsB = List(
    Call(customerB, phoneNumber1, duration1min),
    Call(customerB, phoneNumber2, duration2min),
    Call(customerB, phoneNumber3, duration3min)
  )
  val noPromotions: List[Promotion] = List()
  val idPromotion: Promotion = identity
  val halfPricePromotion: Promotion = { reg => reg.copy(entries = reg.entries.map(entry => entry.copy(cost = entry.cost * 0.5)))}
  val emptyRegister: Register = Register(customerA, List())
  val cost1PerSecond: BigDecimal = BigDecimal.valueOf(1)
  val costFor1Min: BigDecimal = BigDecimal.valueOf(0.05) * 60
  val costFor2Min: BigDecimal = BigDecimal.valueOf(0.05) * 120
  val registerEntriesPhoneNumber1: List[RegisterEntry] = List(
    RegisterEntry(phoneNumber1, duration1min, costFor1Min),
    RegisterEntry(phoneNumber1, duration1min, costFor1Min),
    RegisterEntry(phoneNumber1, duration1min, costFor1Min)
  )
  val registerAPhoneNumber1: Register = Register(customerA, registerEntriesPhoneNumber1)
  val registerEntriesPhoneNumber1AsMaxTotalCost: List[RegisterEntry] = List(
    RegisterEntry(phoneNumber1, duration1min, costFor1Min),
    RegisterEntry(phoneNumber1, duration1min, costFor1Min),
    RegisterEntry(phoneNumber1, duration1min, costFor1Min),
    RegisterEntry(phoneNumber2, duration2min, costFor2Min)
  )
  val registerBPhoneNumber1AsMaxTotalCost: Register = Register(customerB, registerEntriesPhoneNumber1AsMaxTotalCost)
}
