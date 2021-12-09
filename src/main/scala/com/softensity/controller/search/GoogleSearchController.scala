package com.softensity.controller.search

import com.softensity.controller.configs.Constants.necessaryPosition
import com.softensity.service.SearchInfo
import com.softensity.service.ServiceManager.googleSearchController
import org.apache.log4j.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

object GoogleSearchController {
  val LOGGER = Logger.getLogger(this.getClass.getName)

  def getSecondGoogleSearch(query: String): Future[List[SearchInfo]] = {
    LOGGER.debug(s"Getting $necessaryPosition search result from Google ")
    val listOfData = googleSearchController.getSearchResults(query)
    listOfData.map(data => Try(List(data(necessaryPosition))).getOrElse({
      LOGGER.warn("There is no second result")
      List.empty
    }))
  }
}
