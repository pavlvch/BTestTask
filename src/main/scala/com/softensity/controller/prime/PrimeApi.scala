package com.softensity.controller.prime

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.softensity.service.ServiceManager.primeNumberService
import com.softensity.service.{PrimeNumberService, ServiceManager}
import spray.json.DefaultJsonProtocol.{IntJsonFormat, immSeqFormat, listFormat}

import scala.language.postfixOps

trait PrimeApi {
  val primeRoute: Route = {
    get {
      pathPrefix("primes") {
        getPrimesByAlgorithm("algorithm")
      }
    }
  }

  private def getPrimesByAlgorithm(primeParameter: String) = {
    pathPrefix(IntNumber) {
      numberTo =>
        parameter(primeParameter ?) {
          algorithm =>
            validate(numberTo > 2 && numberTo < 10000000,
              "Number must be more then 2 and less then 1000000") {
              algorithm match {
                case Some("stream") => complete(StatusCodes.OK, primeNumberService.findPrimeNumberStream(numberTo))
                case Some("function") => complete(StatusCodes.OK, primeNumberService.findPrimeNumberFunctionally(numberTo))
                case Some(_) => complete(StatusCodes.BadRequest)
              }
            }
        }
    }
  }
}
