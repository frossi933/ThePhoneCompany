package com.phone.company
package services

import model.{Call, Register, RegisterEntry}

import cats.data.ReaderT
import cats.effect.IO
import cats.implicits.toTraverseOps
import repositories.components.Repositories.callsRepository
import services.components.Services.costsService


object RegisterServiceImpl extends RegisterService[IO] {

  override def getRegisters(): ReaderT[IO, Env, Seq[Register]] = {
    for {
      calls <- callsRepository.flatMap(_.read())
      registers <- calls.groupBy(_.customer).map({
        case (customer, calls) => registerEntriesFromCalls(calls).map(entries => Register(customer, entries))
      }).toSeq.sequence
    } yield registers
  }

  private def registerEntriesFromCalls(calls: Seq[Call]): ReaderT[IO, Env, Seq[RegisterEntry]] = {
    costsService.flatMap { costsService =>
      calls.map(call => {
        costsService.calculateUnitCost(call.duration)
          .mapF(_.attempt.flatMap {
            case Left(exception) => {
              IO.println(s"Couldn't calculate cost for call: $call. ${exception.getMessage}. Skipping entry...") *>
                IO.pure(List.empty[RegisterEntry])
            }
            case Right(cost) => IO.pure(List(RegisterEntry(call.phoneNumber, call.duration, cost)))
          })
      }).sequence.map(_.flatten)
    }
  }
}
