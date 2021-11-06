package com.phone.company
package repositories

import org.scalatest.freespec.AnyFreeSpec

class InMemoryPromotionsRepositorySpec extends AnyFreeSpec {

  "greatestTotalCostPhoneForFree promotion" - {
    "should return an empty register if register is empty" in new Fixture {
      val res = InMemoryPromotionsRepository.greatestTotalCostPhoneForFree(emptyRegister)
      assert(res.entries.size == 0)
    }

    "should return a register with total cost equal to zero if register has only entries to the same number" in new Fixture {
      val res = InMemoryPromotionsRepository.greatestTotalCostPhoneForFree(registerAPhoneNumber1)
      assert(res.totalCost == BigDecimal(0))
    }

    "should reduce cost to zero only for the calls made to the phone number with greatest total cost and the rest should be unmodified" in new Fixture {
      val res = InMemoryPromotionsRepository.greatestTotalCostPhoneForFree(registerBPhoneNumber1AsMaxTotalCost)
      assert(res.entries.size == registerBPhoneNumber1AsMaxTotalCost.entries.size
        && res.entries.filter(_.phoneNumber == phoneNumber1).forall(_.cost == 0)
        && res.entries.filterNot(_.phoneNumber == phoneNumber1).map(_.cost) == registerBPhoneNumber1AsMaxTotalCost.entries.filterNot(_.phoneNumber == phoneNumber1).map(_.cost)
      )
    }
  }
}
