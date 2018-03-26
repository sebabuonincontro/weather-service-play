package repository

import javax.inject.Singleton

import com.google.inject.Inject
import domain.Weather
import play.api.{Configuration, Logger}

import scala.concurrent.Future

@Singleton
class WeatherRepository @Inject() (configuration: Configuration) {

  val logger = Logger(this.getClass)

  def create(weather: Weather) = {
    logger.info(s"new weather created. $weather")
    Future.successful(weather)
  }
}
