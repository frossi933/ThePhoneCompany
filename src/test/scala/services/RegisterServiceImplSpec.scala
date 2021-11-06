package com.phone.company
package services

import cats.effect.unsafe.implicits.global
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.freespec.AnyFreeSpec

import java.time.Duration

class RegisterServiceImplSpec extends AnyFreeSpec {

  trait RegisterServiceTesting extends TestEnv with Fixture {
    override val env: Env = new MockEnv
    override def registerService = RegisterServiceImpl
  }

  "getRegisters" - {
    "should return an empty list of register if calls list is empty" in new RegisterServiceTesting {
      when(callsRepository.read()).thenReturn(lift(List()))
      val res = run(registerService.getRegisters()).unsafeRunSync()
      assert(res.size == 0)
    }

    "should contain every call returned by call repository" in new RegisterServiceTesting {
      when(callsRepository.read()).thenReturn(lift(callsA))
      when(costsService.calculateUnitCost(any[Duration])).thenReturn(lift(BigDecimal(1)))
      val res = run(registerService.getRegisters()).unsafeRunSync()
      assert(
        callsA.size == res.map(_.entries.size).sum
        && callsA.forall { call =>
          res.exists(reg =>
            reg.customer == call.customer && reg.entries.exists(entry => entry.phoneNumber == call.phoneNumber && entry.duration == call.duration)
          )
        }
      )
    }
  }
}
