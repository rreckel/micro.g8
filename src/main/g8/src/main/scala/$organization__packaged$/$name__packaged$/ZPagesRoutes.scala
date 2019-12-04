/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  4, 2019
  * Time: 17:00:57
  */

object ZPagesRoutes
    extends ZPagesApi
    with endpoints.akkahttp.server.Endpoints
    with endpoints.akkahttp.server.JsonEntitiesFromCodec {

  def routes = {
    healthz.implementedBy(_ => "OK")
  }
}
