package com.softensity.controller

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.softensity.controller.prime.PrimeApi
import com.softensity.controller.search.SearchApi

trait Routes extends PrimeApi with SearchApi {
  val routes: Route = primeRoute ~ searchRoute
}
