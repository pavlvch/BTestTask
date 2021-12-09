package com.softensity.controller.configs

import com.typesafe.config.{Config, ConfigFactory}

object Settings {
  private val config = ConfigFactory.load
  private val httpConfig = config.getConfig("http")
  val httpInterface: String = httpConfig.getString("interface")
  val httpPort: Int = httpConfig.getInt("port")
  val googleSearchApiKey: String = httpConfig.getString("serpApiKey")
  val googleSearchApiUri: String = httpConfig.getString("serpApiUri")
}

