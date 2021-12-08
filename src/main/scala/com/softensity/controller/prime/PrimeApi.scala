package com.softensity.controller.prime

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.softensity.service.PrimeNumberService
import spray.json.DefaultJsonProtocol.{IntJsonFormat, immSeqFormat, listFormat}

import scala.language.postfixOps

trait PrimeApi {
  val primeRoute: Route =
    get {
      pathPrefix("primes") {
        pathPrefix(IntNumber) {
          number =>
            parameter("algorithm" ?) {
              case Some("stream") => complete(StatusCodes.OK, PrimeNumberService.findPrimeNumberStream(number))
              case Some("function") => complete(StatusCodes.OK, PrimeNumberService.findPrimeNumberFunctionally(number))
              case Some(_) => complete(StatusCodes.BadRequest)
            }
        }
      }
    }
}
