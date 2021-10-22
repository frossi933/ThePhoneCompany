package com.phone.company
package services

import cats.effect.{IO, Resource}
import config.Config
import model.{Call, Commons, Customer, Register}
import utils.DurationUtils

import cats.implicits.toTraverseOps

import scala.io.{BufferedSource, Source}

class FileRegisterService extends RegisterService[IO] {

  private def fileFromSource(name: String): Resource[IO, BufferedSource] = Resource.make(IO.pure(Source.fromFile(name)))(buff => IO(buff.close))

  private def parseCalls(lines: Seq[String]): Seq[Call] = lines.map(_.split(Commons.FORMAT_STRING_SEPARATOR)).flatMap {
    case Array(customer, phone, dur) => List(Call(Customer(customer), phone, DurationUtils.fromString(dur)))
    case _ => List.empty[Call]
  }

  override def read(): IO[Seq[Register]] = fileFromSource(Config.REGISTER_FILENAME).use { buff =>
    IO(buff.getLines().toSeq).map(parseCalls)
  }.flatMap(_.groupBy(_.customer).map { case (customer, calls) => IO.fromTry(Register.make(customer, calls)) }.toSeq.sequence)

  override def write(calls: Seq[Register]): IO[Unit] = ???
}
