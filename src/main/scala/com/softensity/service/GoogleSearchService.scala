package com.softensity.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, Uri}
import com.softensity.controller.configs.Constants
import com.softensity.controller.configs.Settings.{googleSearchApiKey, googleSearchApiUri}
import io.circe._
import io.circe.parser._
import org.apache.log4j.Logger

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

case class SearchInfo(title: String, link: String, snippet: String)

object DataJsonMapping {
  implicit val decodeUser: Decoder[SearchInfo] =
    Decoder.forProduct3("title", "link", "snippet")(SearchInfo.apply)
}

/*
  SerpApi (https://serpapi.com/) API was used for getting results of Google Search
*/

class GoogleSearchService(implicit system: ActorSystem, executor: ExecutionContext) {

  import DataJsonMapping._

  val LOGGER = Logger.getLogger(this.getClass.getName)


  def getSearchResults(query: String): Future[List[SearchInfo]] = {
    val apiKey = googleSearchApiKey
    val apiUri = googleSearchApiUri
    LOGGER.debug("Making the request to SerpAPI")
    val params = Map(
      Constants.engineAttributes,
      Constants.queryAttributes -> query,
      Constants.apiKayAttributes-> apiKey
    )
    val request = HttpRequest(method = HttpMethods.GET, uri = Uri(apiUri).withQuery(Query(params)))
    val responseFut = Http(system).singleRequest(request)
    val entityRequest = responseFut.map(_._3.toStrict(5 seconds)).flatMap(_.map(_.data.utf8String))

    entityRequest.map(decodeGoogleSearchRequest)
  }

  private def decodeGoogleSearchRequest(jsonString: String): List[SearchInfo] = {
    LOGGER.debug("Decoding string into JSON")
    (parse(jsonString).getOrElse(Json.Null) \\ "organic_results").headOption.getOrElse(Json.Null)
      .as[List[SearchInfo]].getOrElse(List.empty)
  }
}
