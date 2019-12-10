/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$
package routes

import $organization$.$name$.api.$name;format="Camel"$Api
import $organization$.$name$.modules.$name;format="Camel"$Programs

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import cats._
import cats.implicits._
import cats.mtl._
import cats.mtl.implicits._
import cats.effect._

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  4, 2019
  * Time: 16:59:43
  */

object $name;format="cap"$Routes
    extends $name;format="Camel"$Api
    with endpoints.akkahttp.server.Endpoints
    with endpoints.akkahttp.server.JsonEntitiesFromCodec
    with EndpointUtils
    with auth.AuthUtils {

  def routes[F[_]: Monad: LiftIO]($name;format="camel"$Programs: $name;format="Camel"$Programs[DomainEffect])(implicit A: ApplicativeAsk[F, Environment]): F[Route] = for {
    env <- A.ask
    _ <- env.logger.debug("Creating $name;format="camel"$Routes").to[F]
  } yield {
    hello.implementedByProgram(env) { token => runWith$name;format="Camel"$User(token){ $name;format="camel"$Programs.sayHello() }} ~
    explode.implementedByProgram(env) {_ => $name;format="camel"$Programs.explode() } ~
    exception.implementedByProgram(env) {_ => $name;format="camel"$Programs.exception() }
  }

  private def runWith$name;format="cap"$User[F[_]: Monad: LiftIO, B](token: String)(f: => F[B])(implicit A: ApplicativeLocal[F, Environment], ME: MonadError[F, Throwable]): F[B] = {
    runWithAuthentication[F, B]("$name;format="upper"$_USER")(token)(f)
  }

}
