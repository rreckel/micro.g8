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
  * 
  * Collection of all the Api's, if we want to create a client for the
  * whole Api
  * TODO: Shoukd be moved to the shared module, along with the other Api traits
  */
trait Api
    extends algebra.Endpoints
    with algebra.circe.JsonEntitiesFromCodec
    with ZPagesApi
    with DemoApi
