package com.softensity.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, Uri}
import com.softensity.Application.configData
import io.circe._
import io.circe.parser._

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps
import scala.util.Try

case class SearchInfo(title: String, link: String, snippet: String)

trait DataJsonMapping {
  implicit val decodeUser: Decoder[SearchInfo] =
    Decoder.forProduct3("title", "link", "snippet")(SearchInfo.apply)
}

object GoogleSearchService extends DataJsonMapping {
  private implicit val system: ActorSystem = ActorSystem()
  protected implicit val executor: ExecutionContext = system.dispatcher

  def makeGoogleRequest(query: String): Future[List[SearchInfo]] = {
    val apiKey = configData.googleSearchApiKey
    val params = Map(
      "engine" -> "google",
      "q" -> query,
      "api_key" -> apiKey
    )
    val request = HttpRequest(method = HttpMethods.GET, uri = Uri("https://serpapi.com/search.json").withQuery(Query(params)))
    val responseFut = Http(system).singleRequest(request)
    val entityRequest = responseFut.map(_._3.toStrict(5 seconds)).flatMap(_.map(_.data.utf8String))

    entityRequest.map(decodeGoogleSearchRequest)
  }

  private def decodeGoogleSearchRequest(jsonString: String): List[SearchInfo] = {
    val list = Try((parse(jsonString).getOrElse(Json.Null) \\ "organic_results").head).getOrElse(Json.Null)
    list.as[List[SearchInfo]].getOrElse(List.empty)
  }
}
