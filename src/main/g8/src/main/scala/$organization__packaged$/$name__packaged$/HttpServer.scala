/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

import cats.implicits._
import cats.effect.{ExitCode, IO, IOApp}

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  4, 2019
  * Time:  9:24:28
  */
object HttpServer extends IOApp {

  implicit val actorSystem                = ActorSystem("$name$-system")
  implicit val materializer               = ActorMaterializer()
  implicit val executor: ExecutionContext = actorSystem.dispatcher

  def run(args: List[String]): IO[ExitCode] = {
    IO.fromFuture {
      IO {
        Http().bindAndHandle(ApiRoutes.routes, "0.0.0.0", 8080)
      } *> IO.never
    }
  }
}
