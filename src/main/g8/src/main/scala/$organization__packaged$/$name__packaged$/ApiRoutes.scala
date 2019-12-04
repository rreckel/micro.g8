/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$

import akka.http.scaladsl.server.Directives._

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  4, 2019
  * Time: 11:53:21
  */
object ApiRoutes
    extends Api
    with endpoints.akkahttp.server.Endpoints
    with endpoints.akkahttp.server.JsonEntitiesFromCodec {

  def routes = {

    hello.implementedBy(_ => "Hello World") ~
      healthz.implementedBy(_ => "OK")
  }
}
