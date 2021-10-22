package com.phone.company
package model

import model.Commons.{Duration, PhoneNumber}

case class RegisterEntry(phoneNumber: PhoneNumber, duration: Duration, cost: BigDecimal)

