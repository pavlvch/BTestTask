package com.softensity.controller.search

import com.softensity.service.GoogleSearchService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

object GoogleSearchController {
  def getSecondGoogleSearch(query: String) = {
    val position = 2
    val listOfData = GoogleSearchService.makeGoogleRequest(query)
    listOfData.map(data => Try(List(data(position))).getOrElse(List.empty))
  }
}
