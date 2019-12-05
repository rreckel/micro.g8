/*
 * Copyright (c) 2019. Ville de Luxembourg. All rights reserved.
 */

package $organization$.$name$

import $organization$.$name$.api.ApiResponse

/**
  * Created by Emacs.
  * User: Roland RECKEL
  * Date: Dec  5, 2019
  * Time: 13:49:43
  */

package routes {
  trait EndpointUtils extends endpoints.akkahttp.server.Endpoints {

    implicit class EndpointOps[A, B](endpoint: Endpoint[A, ApiResponse[B]]) {
      def implementedByProgram(env: Environment)(program: A => DomainEffect[B]) = endpoint.implementedBy {
        a => program(a).toApiResponse().runWithEnv(env)
      }
    }
  }
}
