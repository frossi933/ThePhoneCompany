package com.phone.company
package model

import model.Commons.Duration
import config.Config.costsPerDuration

import cats.Show
import cats.implicits.toTraverseOps
import cats.implicits._
import model.Customer.showCustomer

import scala.util.{Failure, Success, Try}

case class Register(customer: Customer, entries: Seq[RegisterEntry]) {

  def totalCost: BigDecimal = entries.map(_.cost).sum

}

object Register {

  private def calculateCost(duration: Duration): Try[BigDecimal] = costsPerDuration.find(_._1.contains(duration)) match {
    case Some((_, costPerSecond)) => Success(costPerSecond * BigDecimal.valueOf(duration.toSeconds))
    case None => Failure(new Exception("call duration not in configured ranges"))
  }

  def make(customer: Customer, calls: Seq[Call]): Try[Register] = {
    calls.map(call => {
      calculateCost(call.duration).map(cost => RegisterEntry(call.phoneNumber, call.duration, cost))
    }).sequence.map(entries => Register.apply(customer, entries))
  }

  implicit val showRegister: Show[Register] = Show.show(reg => s"${reg.customer.show}: ${reg.totalCost}")
}