package com.phone.company
package repositories

import model.Commons.{PhoneNumber, Promotion}
import model.Customer
import cats.effect.IO


object InMemoryPromotionsRepository extends PromotionsRepository[IO] {

  override def getByCustomer(customer: Customer): IO[List[Promotion]] = IO.pure(List(greatestTotalCostPhoneForFree))

  val greatestTotalCostPhoneForFree: Promotion = { reg =>
    if(reg.entries.isEmpty)
      reg
    else {
      val totalCostByPhoneNumber = reg.entries.groupMapReduce(_.phoneNumber)(_.cost)(_ + _)
      val greatestTotalCostPhoneNumber = totalCostByPhoneNumber.maxBy(_._2)._1
      reg.copy(entries = reg.entries.map {
        case entry if entry.phoneNumber.equals(greatestTotalCostPhoneNumber) => entry.copy(cost = BigDecimal.valueOf(0))
        case entry => entry
      })
    }
  }

  // More examples of promotions applicable following this design
  val mostExpensiveCallForFree: Promotion = { reg =>
    val costOrderingInverse: Ordering[BigDecimal] = Ordering.BigDecimal.reverse
    reg.copy(entries = reg.entries.sortBy(_.cost)(costOrderingInverse).tail)
  }
  val discount25: Promotion = { reg =>
    reg.copy(entries = reg.entries.map(call => call.copy(cost = call.cost * 0.75)))
  }
  val freeCallsTo: Promotion = { reg =>
    reg.copy(entries = reg.entries.filter(_.phoneNumber.equals(new PhoneNumber("123-123-123"))))
  }

}
