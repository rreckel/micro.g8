/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$

import cats.implicits._
import cats.effect.{ ExitCode, IO, IOApp }

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  4, 2019
  * Time:  9:24:28
  */

object HttpServer extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    IO(println("Hello World")).as(ExitCode.Success)
  }
}
