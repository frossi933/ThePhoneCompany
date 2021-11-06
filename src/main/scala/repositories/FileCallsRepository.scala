package com.phone.company
package repositories

import model.{Call, Commons, Customer}

import utils.DurationUtils
import cats.data.ReaderT
import cats.effect.{IO, Resource}
import cats.implicits.toTraverseOps
import scala.io.{BufferedSource, Source}
import scala.util.{Failure, Success}


object FileCallsRepository extends CallsRepository[IO] {

  import Env.config

  def read(): ReaderT[IO, Env, Seq[Call]] = for {
    config <- config
    calls <- ReaderT.liftF(fileFromSource(config.REGISTER_FILENAME).use {
      case Some(buff) => parseCalls(buff.getLines().toSeq)
      case None       => IO(Seq.empty[Call])
    })
  } yield calls

  private def fileFromSource(name: String): Resource[IO, Option[BufferedSource]] = Resource.make {
    IO.delay(Source.fromFile(name)).attempt.flatMap{
      case Left(exception) => IO.println(s"Couldn't read calls from file \"$name\" ${exception.getMessage}") *> IO(None)
      case Right(file) => IO(Some(file))
    }
  }(buff => IO(buff.map(_.close)))

  private def parseCalls(lines: Seq[String]): IO[Seq[Call]] = lines.map(_.split(Commons.FORMAT_STRING_SEPARATOR)).zip(1 to lines.size).map {
    case (Array(customer, phone, dur), lineNumber) => DurationUtils.fromString(dur) match {
      case Success(duration) => IO.pure(List(Call(Customer(customer), phone, duration)))
      case Failure(_) => IO.println(s"line $lineNumber: Couldn't parse call duration") *> IO.pure(List())
    }
    case (Array(""), lineNumber) => IO.println(s"Line $lineNumber: line is empty") *> IO.pure(List())
    case (_, lineNumber) => IO.println(s"line $lineNumber: Malformed Call entry, skipping...") *> IO.pure(List())
  }.sequence.map(_.flatten)

}
