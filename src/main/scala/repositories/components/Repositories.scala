package com.phone.company
package repositories.components


trait Repositories extends CallsRepositoryComponent with CostsRepositoryComponent with PromotionsRepositoryComponent

object Repositories {

  import Env.repositories

  val callsRepository = repositories.map(_.callsRepository)
  val costsRepository = repositories.map(_.costsRepository)
  val promotionsRepository = repositories.map(_.promotionsRepository)

}
