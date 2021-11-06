package com.phone.company
package repositories

import model.Call
import cats.data.ReaderT


trait CallsRepository[F[_]] {

  def read(): ReaderT[F, Env, Seq[Call]]

}
