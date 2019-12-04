/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

import cats.implicits._
import cats.effect.{ExitCode, IO, IOApp}

import io.chrisdavenport.log4cats.slf4j.Slf4jLogger

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
  def server(routes: Route) = IO.fromFuture {
    IO {
      Http().bindAndHandle(routes, "0.0.0.0", 8080)
    } *> IO.never
  }

  /**
    * Main entrypoint.
    * The server wide initialization is made here and the server is executed
    */
  def run(args: List[String]): IO[ExitCode] = {

    Slf4jLogger.create[IO].flatMap { implicit logger =>
      for {
        routes <- IO{ ApiRoutes.routes }
        s <- server(routes)
      } yield s
    }
  }
}
