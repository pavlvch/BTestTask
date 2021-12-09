package com.softensity.service

import akka.actor.ActorSystem
import com.softensity.controller.search.GoogleSearchController

import scala.concurrent.ExecutionContext

object ServiceManager {
  private implicit val system: ActorSystem = ActorSystem()
  private implicit val executor: ExecutionContext = system.dispatcher
  val googleSearchController = new GoogleSearchService
  val primeNumberService = new PrimeNumberService
}
