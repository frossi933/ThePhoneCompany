package com.phone.company

import cats.Monad
import cats.data.ReaderT

trait TestEnv {
  def env: Env

  def config = env.config
  def repositories = env.repositories
  def services = env.services
  def callsRepository = repositories.callsRepository
  def costsRepository = repositories.costsRepository
  def promotionsRepository = repositories.promotionsRepository
  def promotionsService = services.promotionsService
  def costsService = services.costsService
  def registerService = services.registerService

  def lift[A, F[_]](a : A)(implicit ev: Monad[F]): ReaderT[F, Env, A] = ReaderT.liftF(ev.pure(a))

  def run[A, F[_]](readerT: ReaderT[F, Env, A]) = readerT.run(env)
}
