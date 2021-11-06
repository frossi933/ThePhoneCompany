package com.phone.company
package services

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import model.Commons.MisconfigurationException
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when
import org.scalatest.freespec.AnyFreeSpec

import java.time.Duration

class CostsServiceImplSpec extends AnyFreeSpec {

  trait CostsServiceTesting extends TestEnv with Fixture {
    override val env: Env = new MockEnv
    override def costsService = CostsServiceImpl
  }

  "calculateUnitCost" - {
    "should raise error if there is no cost configuration for a specific duration" in new CostsServiceTesting {
      when(costsRepository.getCostPerSecondByDuration(any[Duration])).thenReturn(IO(None))
      intercept[MisconfigurationException] {
        run(costsService.calculateUnitCost(duration1min)).unsafeRunSync()
      }
    }

    "should return zero if the given duration is zero" in new CostsServiceTesting {
      when(costsRepository.getCostPerSecondByDuration(any[Duration])).thenReturn(IO(Some(cost1PerSecond)))
      val res = run(costsService.calculateUnitCost(durationZero)).unsafeRunSync()
      assert(res == 0)
    }

    "should return the cost per second multiplied by the number of seconds of the given duration" in new CostsServiceTesting {
      when(costsRepository.getCostPerSecondByDuration(any[Duration])).thenReturn(IO(Some(cost1PerSecond)))
      val res = run(costsService.calculateUnitCost(duration1min)).unsafeRunSync()
      assert(res == cost1PerSecond * duration1min.toSeconds)
    }
  }
}
