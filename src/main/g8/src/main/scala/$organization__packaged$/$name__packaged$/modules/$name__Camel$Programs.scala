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

trait $name;format="Camel"$Programs[F[_]] {

  def sayHello(): F[String]
  def explode(): F[Unit]
  def exception(): F[Unit]
}

object $name;format="Camel"$Programs {

  def $name;format="camel"$Programs[F[_]: Monad: DomainError: LiftIO](implicit A: ApplicativeAsk[F, Environment]) = new $name;format="Camel"$Programs[F] {

    override def sayHello() = for {
      env <- A.ask
      username = env.authConfig.claim.map(_.username)
      _ <- env.logger.debug("Executing sayHello program").to[F]
    } yield {
      s"Hello World \${username.getOrElse("Username not found...?")}"
    }

    case object Boom extends DomainException("The service failed.")
    override def explode() = DomainError[F].raiseError(Boom)

    override def exception() = throw new RuntimeException("An exception is thrown")
  }
}