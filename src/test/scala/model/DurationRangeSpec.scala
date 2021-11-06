package com.phone.company
package model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.TryValues._

import java.time.Duration
import java.time.format.DateTimeParseException

class DurationRangeSpec extends AnyFlatSpec with Matchers {

  "A DurationRange" should "be made from two values of type java.time.Duration" in {
    val durationFrom: Duration = Duration.ZERO
    val durationTo: Duration = Duration.ofMinutes(3)
    noException should be thrownBy DurationRange.make(durationFrom, durationTo)
  }

  it should "be made from two well-formed values of type string" in {
    val stringFrom: String = "00:00:00"
    val stringTo: String = "00:03:00"
    val duration = DurationRange.make(stringFrom, stringTo)
    duration.success.value.from shouldEqual Duration.ZERO
    duration.success.value.to shouldEqual Duration.ofMinutes(3)
  }

  it should "be failed if string values are not correct" in {
    val wrongStringFrom: String = "e0"
    val wrongStringTo: String = "e1"
    noException should be thrownBy DurationRange.make(wrongStringFrom, wrongStringTo)
    val duration = DurationRange.make(wrongStringFrom, wrongStringTo)
    duration.failure.exception shouldBe a[DateTimeParseException]
  }

  "DurationRange.contains" should "return true if the value is greater or equal than From and less or equal than To" in {
    val from = Duration.ZERO
    val to = Duration.ofMinutes(5)
    val range = DurationRange.make(from, to)

    range.contains(from) shouldBe true
    range.contains(to) shouldBe true
    range.contains(Duration.ofSeconds(1)) shouldBe true
    range.contains(Duration.ofMinutes(1)) shouldBe true
  }

  it should "return false if the value is less than From" in {
    val from = Duration.ofMinutes(2)
    val to = Duration.ofMinutes(5)
    val range = DurationRange.make(from, to)

    range.contains(Duration.ofMinutes(2).minusSeconds(1)) shouldBe false
    range.contains(Duration.ZERO) shouldBe false
  }

  it should "return false if the value is greater than To" in {
    val from = Duration.ZERO
    val to = Duration.ofMinutes(5)
    val range = DurationRange.make(from, to)

    range.contains(Duration.ofMinutes(5).plusSeconds(1)) shouldBe false
    range.contains(Duration.ofMinutes(10)) shouldBe false
  }
}
