package com.softensity

import com.typesafe.config.Config

case class Settings(config: Config) {
  private val httpConfig = config.getConfig("http")
  val httpInterface: String = httpConfig.getString("interface")
  val httpPort: Int = httpConfig.getInt("port")
  val googleSearchApiKey: String = httpConfig.getString("serpApiKey")
}
