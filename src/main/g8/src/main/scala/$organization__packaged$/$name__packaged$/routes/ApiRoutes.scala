/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$
package routes

import $organization$.$name$.api.Api
import $organization$.$name$.modules.DemoPrograms

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

  val demoPrograms = DemoPrograms.demoPrograms[DomainEffect]

  def routes[F[_]: Monad: LiftIO](implicit A: ApplicativeAsk[F, Environment]): F[Route]  = for {
    demoRoutes <- DemoRoutes.routes[F](demoPrograms)
  } yield {
    demoRoutes ~
    ZPagesRoutes.routes
  }
}
