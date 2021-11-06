package com.phone.company
import config.{Config, ConfigComponent, LiveConfig}

import cats.effect.IO
import repositories.{CallsRepository, CostsRepository, FileCallsRepository, InMemoryCostsRepository, InMemoryPromotionsRepository, PromotionsRepository}
import repositories.components.{Repositories, RepositoriesComponent}
import services.{CostsService, CostsServiceImpl, PromotionsService, PromotionsServiceImpl, RegisterService, RegisterServiceImpl}
import services.components.{Services, ServicesComponent}


object LiveEnv extends Env with LiveConfigComponent with LiveServicesComponent with LiveRepositoriesComponent

trait LiveConfigComponent extends ConfigComponent {
  override def config: Config = LiveConfig
}

trait LiveServicesComponent extends ServicesComponent {
  override def services: Services = new Services {
    override def promotionsService: PromotionsService[IO] = PromotionsServiceImpl

    override def registerService: RegisterService[IO] = RegisterServiceImpl

    override def costsService: CostsService[IO] = CostsServiceImpl
  }
}

trait LiveRepositoriesComponent extends RepositoriesComponent {
  override def repositories: Repositories = new Repositories {
    override def costsRepository: CostsRepository[IO] = InMemoryCostsRepository

    override def callsRepository: CallsRepository[IO] = FileCallsRepository

    override def promotionsRepository: PromotionsRepository[IO] = InMemoryPromotionsRepository
  }
}