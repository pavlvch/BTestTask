package com.softensity.controller.search

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import com.softensity.service.SearchInfo
import spray.json.{DefaultJsonProtocol, RootJsonFormat, enrichAny}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

trait DirectorsJsonMapping extends DefaultJsonProtocol {
  implicit val directorsFormat: RootJsonFormat[SearchInfo] = jsonFormat3(SearchInfo.apply)
}


trait SearchApi extends DirectorsJsonMapping {
  val searchRoute = get {
    pathPrefix("search") {
      parameter("q") {
        query =>
          complete(GoogleSearchController.getSecondGoogleSearch(query).map(_.toJson))
      }
    }
  }

}
