package com.phone.company
package services.components


trait Services extends CostsServiceComponent with PromotionsServiceComponent with RegisterServiceComponent

object Services {

  import Env.services

  val costsService = services.map(_.costsService)
  val promotionsService = services.map(_.promotionsService)
  val registerService = services.map(_.registerService)

}
