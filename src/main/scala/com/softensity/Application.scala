package com.softensity

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.logRequestResult
import com.softensity.controller.Routes.routes
import com.softensity.controller.configs.Settings.{httpInterface, httpPort}
import org.apache.log4j.Logger

import scala.concurrent.ExecutionContext

object Application extends App {
  private implicit val system: ActorSystem = ActorSystem()
  protected implicit val executor: ExecutionContext = system.dispatcher
  protected val log: LoggingAdapter = Logging(system, getClass)
  val LOGGER = Logger.getLogger(this.getClass.getName)

  LOGGER.info("Program is running")

  LOGGER.debug("Configuration was loaded")
  val bindingFuture = Http()
    .bindAndHandle(handler = logRequestResult("log")(routes)
      , interface = httpInterface, httpPort)
  LOGGER.info("Web app is running")

}
