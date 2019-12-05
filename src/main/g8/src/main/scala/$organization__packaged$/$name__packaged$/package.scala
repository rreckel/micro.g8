/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$

import $organization$.$name$.api.{ApiResponse, ApiSuccess, ApiFailure, SystemError}

import java.io.PrintWriter
import java.io.StringWriter

import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers._

import cats._
import cats.data._
import cats.implicits._

import cats.effect._

import io.circe.syntax._
import io.circe.generic.auto._

import org.slf4j.LoggerFactory
import io.chrisdavenport.log4cats.Logger

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  5, 2019
  * Time: 10:26:37
  */

package $name$ {

  /**
    * Extend this class if you want to return a DomainError. All other types of Exceptions will be returned as SystemError
    */
  abstract class DomainException(val msg: String) extends Exception(msg)
  
  /**
    * The global environment, passed to the modules
    */
  case class Environment(logger: Logger[IO])

}

package object $name$ {

  type DomainError[F[_]] = MonadError[F, Throwable]
  object DomainError {
    def apply[F[_]](implicit F: DomainError[F]): DomainError[F] = F
  }

  /**
    * This is the effect used by the different modules
    */
  type DomainEffect[T] = ReaderT[IO, Environment, T]


  implicit class DomainExecutor[A](fa: DomainEffect[A]) {

    /**
      * Execute (or run) a DomainEffect with a given Environment
      */
    def runWithEnv(env: Environment): A = fa.run(env).unsafeRunSync()
  }

  implicit class ApiErrorHandler[F[_]: Monad: DomainError: LiftIO, A](fa: F[A]) {

    /** 
      * Extract the ApiResponse of the 'F' type. All we do is handle the ApiError and wrap into ApiResponse
      */
    def toApiResponse(): F[ApiResponse[A]] = fa.map[ApiResponse[A]](a => ApiSuccess(a)).recover{case e: DomainException => ApiFailure(api.DomainError(e.msg, causeList(e), getStackTraceAsString(e)))}.handleErrorWith(e => createSystemError[F](e).map[ApiResponse[A]](identity))
  }

  object AkkaExceptionHandler {
    implicit def exceptionHandler = ExceptionHandler {
      case e => complete(StatusCodes.InternalServerError, unsafeCreateSystemErrorAsString(e))
    }
  }

  private def createSystemError[F[_]: DomainError: LiftIO](e: Throwable): F[ApiFailure] = for {
    logger <- IO(LoggerFactory.getLogger("SystemError")).to[F]
    _ <- IO(logger.error("A SystemError was encountered", e)).to[F]
    f <- IO(ApiFailure(SystemError(e.getMessage(), causeList(e), getStackTraceAsString(e)))).to[F]
  } yield f


  private def unsafeCreateSystemErrorAsString(e: Throwable): String = createSystemError[IO](e).unsafeRunSync.asJson.noSpaces

  private def causeList(e: Throwable): List[String] = {
    def secureMessage(s: String) = Option(s).getOrElse("N/A")
    Option(e.getCause) match {
      case Some(c) => secureMessage(e.getMessage) :: causeList(c)
      case None => secureMessage(e.getMessage) :: Nil
    }
  }

  private def getStackTraceAsString(t: Throwable) = {
    val sw = new StringWriter
    t.printStackTrace(new PrintWriter(sw))
    sw.toString
  }

}
