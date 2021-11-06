package com.phone.company
package repositories

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers.{be, noException}

import scala.language.postfixOps

class InMemoryCostsRepositorySpec extends AnyFreeSpec {

  trait InMemoryCostRepositoryTesting extends TestEnv with Fixture {
    override val env: Env = new MockEnv
    override def costsRepository: CostsRepository[IO] = InMemoryCostsRepository
  }

  "getCostPerSecondByDuration" - {
    "should not throw an exception if duration is out of range" in new InMemoryCostRepositoryTesting {
      noException should be thrownBy costsRepository.getCostPerSecondByDuration(duration1Year)
    }

    "should return none if duration is out of range" in new InMemoryCostRepositoryTesting {
      assert(costsRepository.getCostPerSecondByDuration(duration1Year).unsafeRunSync() == None)
    }
  }
}
