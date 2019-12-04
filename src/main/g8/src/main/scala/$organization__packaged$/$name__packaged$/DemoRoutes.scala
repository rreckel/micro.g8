/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  4, 2019
  * Time: 16:59:43
  */

object DemoRoutes
    extends DemoApi
    with endpoints.akkahttp.server.Endpoints
    with endpoints.akkahttp.server.JsonEntitiesFromCodec {

  def routes = {
    hello.implementedBy(_ => "Hello World")
  }
}

