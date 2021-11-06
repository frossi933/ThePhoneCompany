package com.phone.company

import config.{Config, ConfigComponent}

import cats.effect.IO
import repositories.{CallsRepository, CostsRepository, PromotionsRepository}
import repositories.components.{Repositories, RepositoriesComponent}
import services.{CostsService, PromotionsService, RegisterService}
import services.components.{Services, ServicesComponent}
import org.scalatestplus.mockito.MockitoSugar.mock


class MockEnv extends Env with MockConfigComponent with MockServicesComponent with MockRepositoriesComponent

trait MockConfigComponent extends ConfigComponent {
  override val config = mock[Config]
}

trait MockServicesComponent extends ServicesComponent {
  override val services: Services = new Services {
    override val promotionsService = mock[PromotionsService[IO]]

    override val registerService = mock[RegisterService[IO]]

    override val costsService = mock[CostsService[IO]]
  }
}

trait MockRepositoriesComponent extends RepositoriesComponent {
  override val repositories: Repositories = new Repositories {
    override val costsRepository = mock[CostsRepository[IO]]

    override val callsRepository = mock[CallsRepository[IO]]

    override val promotionsRepository = mock[PromotionsRepository[IO]]
  }
}
