package com.phone.company
package repositories

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.mockito.Mockito.when
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers.{be, noException}

import scala.language.postfixOps


class FileCallsRepositorySpec extends AnyFreeSpec {

  trait CallsRepositoryTesting extends TestEnv with Fixture {
    override val env: Env = new MockEnv
    override def callsRepository: CallsRepository[IO] = FileCallsRepository
  }

  "read" - {
    "should not throw an exception and return an empty list if file doesn't exist" in new CallsRepositoryTesting {
      when(config.REGISTER_FILENAME).thenReturn("")
      noException should be thrownBy run(callsRepository.read()).unsafeRunSync()
    }

    "should return an empty list if file doesn't exist" in new CallsRepositoryTesting {
      when(config.REGISTER_FILENAME).thenReturn("")
      val res = run(callsRepository.read()).unsafeRunSync()
      assert(res.size == 0)
    }

    "should not throw an exception if file is empty" in new CallsRepositoryTesting {
      when(config.REGISTER_FILENAME).thenReturn("src/test/resources/test_calls_empty.txt")
      noException should be thrownBy run(callsRepository.read()).unsafeRunSync()
    }

    "should return an empty list if file is empty" in new CallsRepositoryTesting {
      when(config.REGISTER_FILENAME).thenReturn("src/test/resources/test_calls_empty.txt")
      val res = run(callsRepository.read()).unsafeRunSync()
      assert(res.size == 0)
    }

    "should not throw an exception if file has malformed entries" in new CallsRepositoryTesting {
      when(config.REGISTER_FILENAME).thenReturn("src/test/resources/test_calls_with_errors.txt")
      noException should be thrownBy run(callsRepository.read()).unsafeRunSync()
    }

    "should return only correct lines if file has malformed entries" in new CallsRepositoryTesting {
      when(config.REGISTER_FILENAME).thenReturn("src/test/resources/test_calls_with_errors.txt")
      val res = run(callsRepository.read()).unsafeRunSync()
      assert(res.size == 1 && res.head.customer == customerA && res.head.phoneNumber == phoneNumber1 && res.head.duration == duration1min)
    }

    "should not throw an exception if file is correct" in new CallsRepositoryTesting {
      when(config.REGISTER_FILENAME).thenReturn("src/test/resources/test_calls_correct.txt")
      noException should be thrownBy run(callsRepository.read()).unsafeRunSync()
    }

    "should return only correct lines if file is correct" in new CallsRepositoryTesting {
      when(config.REGISTER_FILENAME).thenReturn("src/test/resources/test_calls_correct.txt")
      val res = run(callsRepository.read()).unsafeRunSync()
      assert(res.size == (callsA.size + callsB.size)
        && res.take(3).toList === callsA
        && res.drop(3).toList == callsB
      )
    }
  }
}
