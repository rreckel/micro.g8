/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$

import endpoints.algebra

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
  val hello = endpoint(get(basePath / "hello"), jsonResponse[String]())

}
