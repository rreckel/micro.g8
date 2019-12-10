/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$
package api

import endpoints.algebra

import io.circe.generic.auto._

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  4, 2019
  * Time: 17:10:50
  */

trait $name;format="cap"$Api
    extends algebra.Endpoints
    with algebra.circe.JsonEntitiesFromCodec {

  val basePath = path / "api" / "v1"

  val authorizationHeader = header("Authorization", Some("Bearer authorization header filled with the JWT token"))

  // Api endpoint
  val hello = endpoint(get(basePath / "hello", authorizationHeader), jsonResponse[ApiResponse[String]]())
  val explode = endpoint(get(basePath / "explode"), jsonResponse[ApiResponse[Unit]]())
  val exception = endpoint(get(basePath / "exception"), jsonResponse[ApiResponse[Unit]]())
}
