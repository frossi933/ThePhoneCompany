package com.phone.company
package services

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import model.Customer
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.freespec.AnyFreeSpec

class PromotionsServiceImplSpec extends AnyFreeSpec {

  trait PromotionsServiceTesting extends TestEnv with Fixture {
    override val env: Env = new MockEnv
    override def promotionsService = PromotionsServiceImpl
  }

  "applyPromotions" - {
    "should return an empty register if its input was empty" in new PromotionsServiceTesting {
      when(promotionsRepository.getByCustomer(any[Customer])).thenReturn(IO(noPromotions))
      val res = run(promotionsService.applyPromotions(emptyRegister)).unsafeRunSync()
      assert(res.entries.size == 0)
    }

    "should return the register without changes if there is no promotions to apply" in new PromotionsServiceTesting {
      when(promotionsRepository.getByCustomer(any[Customer])).thenReturn(IO(noPromotions))
      val res = run(promotionsService.applyPromotions(registerAPhoneNumber1)).unsafeRunSync()
      assert(res == registerAPhoneNumber1)
    }

    "should apply promotions without affecting the order of the entries" in new PromotionsServiceTesting {
      when(promotionsRepository.getByCustomer(any[Customer])).thenReturn(IO(List(idPromotion)))
      val res = run(promotionsService.applyPromotions(registerAPhoneNumber1)).unsafeRunSync()
      assert(res.entries.zip(registerAPhoneNumber1.entries).forall { case (entry1, entry2) => entry1 == entry2 })
    }

    "should apply promotions to every entry in the register" in new PromotionsServiceTesting {
      when(promotionsRepository.getByCustomer(any[Customer])).thenReturn(IO(List(halfPricePromotion)))
      val res = run(promotionsService.applyPromotions(registerAPhoneNumber1)).unsafeRunSync()
      assert(res.entries.zip(registerAPhoneNumber1.entries).forall { case (entry1, entry2) => entry1.cost == entry2.cost * 0.5 })
    }

    "should apply every promotions to every entry in the register" in new PromotionsServiceTesting {
      when(promotionsRepository.getByCustomer(any[Customer])).thenReturn(IO(List(halfPricePromotion, halfPricePromotion)))
      val res = run(promotionsService.applyPromotions(registerAPhoneNumber1)).unsafeRunSync()
      assert(res.entries.zip(registerAPhoneNumber1.entries).forall { case (entry1, entry2) => entry1.cost == entry2.cost * 0.5 * 0.5 })
    }
  }
}
