/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$

import endpoints.algebra

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  4, 2019
  * Time: 17:03:25
  */

trait ZPagesApi
    extends algebra.Endpoints
    with algebra.circe.JsonEntitiesFromCodec {

  val healthz = endpoint(get(path / "healthz"), jsonResponse[String]())
}
