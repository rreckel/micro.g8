/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$

import $organization$.$name$.routes.ApiRoutes

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

import cats._
import cats.data._
import cats.implicits._
import cats.effect._
import cats.mtl._
import cats.mtl.implicits._

import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import io.chrisdavenport.log4cats.Logger

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  4, 2019
  * Time:  9:24:28
  * 
  * This is the main entry point of the application. It uses the cats
  * IOApp to launch the HttpServer
  */
object HttpServer extends IOApp {

  implicit val actorSystem                = ActorSystem("$name$-system")
  implicit val materializer               = ActorMaterializer()
  implicit val executor: ExecutionContext = actorSystem.dispatcher


  /**
    * Creates a server instance using the specified routes
    */
  def server[F[_]: LiftIO](routes: Route) = IO.fromFuture {
    IO {
      Http().bindAndHandle(routes, "0.0.0.0", 8080)
    } *> IO.never
  }.as(ExitCode.Success).to[F]

  /**
    * Main entrypoint.
    * The server wide initialization is made here and the server is executed
    */
  def run(args: List[String]): IO[ExitCode] = {

    type RoutesEffect[T] = ReaderT[IO, Environment, T]

    for {
      logger <- Slf4jLogger.create[IO]
      env = Environment(logger)
      routes <- ApiRoutes.routes[RoutesEffect].run(env)
      s <- server[IO](routes)
    } yield s    
  }
}
