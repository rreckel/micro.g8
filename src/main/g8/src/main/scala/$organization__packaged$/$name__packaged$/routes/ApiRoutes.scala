/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$
package routes

import $organization$.$name$.api.Api
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
  * Time: 11:53:21
  * 
  * The Akka server routes are defined here
  */
object ApiRoutes
    extends Api
    with endpoints.akkahttp.server.Endpoints
    with endpoints.akkahttp.server.JsonEntitiesFromCodec {

  val $name;format="camel"$Programs = $name;format="Camel"$Programs.$name;format="camel"$Programs[DomainEffect]

  def routes[F[_]: Monad: LiftIO](implicit A: ApplicativeAsk[F, Environment]): F[Route]  = for {
    $name;format="camel"$Routes <- $name;format="Camel"$Routes.routes[F]($name;format="camel"$Programs)
  } yield {
    $name;format="camel"$Routes ~
    ZPagesRoutes.routes
  }
}
