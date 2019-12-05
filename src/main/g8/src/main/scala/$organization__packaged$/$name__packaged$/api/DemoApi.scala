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

trait DemoApi
    extends algebra.Endpoints
    with algebra.circe.JsonEntitiesFromCodec {

  val basePath = path / "api" / "v1"

  // Api endpoint
  val hello = endpoint(get(basePath / "hello"), jsonResponse[ApiResponse[String]]())
  val explode = endpoint(get(basePath / "explode"), jsonResponse[ApiResponse[Unit]]())
  val exception = endpoint(get(basePath / "exception"), jsonResponse[ApiResponse[Unit]]())
}
