/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$

import endpoints.algebra

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  4, 2019
  * Time: 11:45:16
  */
trait Api extends algebra.Endpoints with algebra.circe.JsonEntitiesFromCodec {

  val basePath = path / "api" / "v1"

  // Api endpoint
  val hello = endpoint(get(basePath / "hello"), jsonResponse[String]())

  // z-Pages
  val healthz = endpoint(get(path / "healthz"), jsonResponse[String]())
}
