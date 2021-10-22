package com.phone.company
package services

import model.Customer
import model.Commons.{PhoneNumber, Promotion}

import cats.effect.IO

class CustomPromotionsService extends PromotionsService[IO] {

  implicit val costOrderingInverse: Ordering[BigDecimal] = Ordering.BigDecimal.reverse
  val mostExpensiveCallForFree: Promotion = { reg =>
    reg.copy(entries = reg.entries.sortBy(_.cost)(costOrderingInverse).tail)
  }

  // More examples of promotions applicable following this design
  val discount25: Promotion = { reg =>
    reg.copy(entries = reg.entries.map(call => call.copy(cost = call.cost * 0.75)))
  }
  val freeCallsTo: Promotion = { reg =>
    reg.copy(entries = reg.entries.filter(_.phoneNumber.equals(new PhoneNumber("123-123-123"))))
  }

  def getByCustomer(customer: Customer): IO[List[Promotion]] = IO.pure(List(mostExpensiveCallForFree))
}
