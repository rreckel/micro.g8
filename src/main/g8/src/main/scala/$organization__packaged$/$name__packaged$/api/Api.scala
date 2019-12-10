/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$
package api

import endpoints.algebra

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  4, 2019
  * Time: 11:45:16
  * 
  * Collection of all the Api's, if we want to create a client for the
  * whole Api
  * TODO: Shoukd be moved to the shared module, along with the other Api traits
  */
trait Api
    extends algebra.Endpoints
    with algebra.circe.JsonEntitiesFromCodec
    with ZPagesApi
    with $name;format="Camel"$Api

sealed trait ApiError {
  def msg: String
  def cause: List[String]
  def stacktrace: String
}
case class DomainError(msg: String, cause: List[String], stacktrace: String) extends ApiError
case class SystemError(msg: String, cause: List[String], stacktrace: String) extends ApiError

sealed trait ApiResponse[+T]
case class ApiSuccess[T](result: T) extends ApiResponse[T]
case class ApiFailure(failure: ApiError) extends ApiResponse[Nothing]
