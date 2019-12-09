/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$
package auth

import lu.vdl.keymaker.KeymakerUtils
import lu.vdl.keymaker.Claim

import cats._
import cats.data._
import cats.implicits._
import cats.effect._
import cats.mtl._

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  9, 2019
  * Time: 10:54:56
  */

trait AuthUtils extends {

  def runWithAuthentication[F[_]: Monad: LiftIO, B](role: String)(token: String)(f: => F[B])(implicit A: ApplicativeLocal[F, Environment], ME: MonadError[F, Throwable]): F[B] = for {
    c <- authenticate[F](token, role)
    r <- A.local(e => e.copy(authConfig = e.authConfig.copy(claim = Some(c))))(f)
  } yield r

  def decodeToken[F[_]: Monad: LiftIO](token: String)(implicit A: ApplicativeAsk[F, Environment]) = for {
    env <- A.ask
    publicKey = env.authConfig.keymakerPublicKey
    claim <- KeymakerUtils.decodeTokenWithPublicKeyString(publicKey)(token).to[F]
  } yield claim

  case class AuthenticationError(message: String) extends DomainException(s"Authentification incorrecte. Veuillez recharger l'application. \n \$message")
  case class AuthorizationError(c: Claim, role: String) extends DomainException(s"User \${c.username} with roles \${c.roles.mkString(", ")} wanted to access a ressource with \$role access right")

  def authenticate[F[_]: Monad: LiftIO](token: String, role: String)(implicit A: ApplicativeAsk[F, Environment], ME: MonadError[F, Throwable]): F[Claim] = for {
    r <- decodeToken[F](token)
    claim <- Monad[F].pure(r.leftMap(AuthenticationError)).rethrow
    _ <- Monad[F].pure(claim).ensure(AuthorizationError(claim, role))(c => c.roles.toList contains role)
  } yield claim  
  
}
