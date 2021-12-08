package com.softensity

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.logRequestResult
import com.softensity.controller.Routes
import com.softensity.service.GoogleSearchService
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext

object Application extends App with Routes {
  private implicit val system: ActorSystem = ActorSystem()
  protected implicit val executor: ExecutionContext = system.dispatcher
  protected val log: LoggingAdapter = Logging(system, getClass)

  val configData: Settings = Settings(ConfigFactory.load)

  val bindingFuture = Http()
    .bindAndHandle(handler = logRequestResult("log")(routes)
      , interface = configData.httpInterface, port = configData.httpPort)

}
