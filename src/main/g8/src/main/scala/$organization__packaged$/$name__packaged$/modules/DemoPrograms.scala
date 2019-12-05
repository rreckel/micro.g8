/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$
package modules

import cats._
import cats.implicits._
import cats.effect._
import cats.mtl._


/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  5, 2019
  * Time: 10:07:45
  */

trait DemoPrograms[F[_]] {

  def sayHello(): F[String]
}

object DemoPrograms {

  def demoPrograms[F[_]: Monad: LiftIO](implicit A: ApplicativeAsk[F, Environment]) = new DemoPrograms[F] {
    override def sayHello() = for {
      env <- A.ask
      _ <- env.logger.debug("Executing sayHello program").to[F]
    } yield {
      "Hello World"
    }
  }
}
