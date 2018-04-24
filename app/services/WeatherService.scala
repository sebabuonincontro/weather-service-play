package services

import com.google.inject.Inject
import domain.Weather
import listeners.WeatherCreateProducer
import play.api.{Configuration, Logger}
import repository.WeatherRepository
import domain.ImplicitsJson._
import play.api.libs.json.Json

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class WeatherService @Inject() (configuration: Configuration,
                                weatherCreateProducer: WeatherCreateProducer,
                                weatherRepository: WeatherRepository) {

  val logger = Logger(this.getClass)

  val list = List(
    Weather(Some(1), "description1"),
    Weather(Some(2), "description2"),
    Weather(Some(3), "description3")
  )

  def getAll(): Future[List[Weather]] = {
    weatherRepository.getAll().map { map =>
      map
        .toList
        .map(_._2)
        .map(w => Json.fromJson(Json.parse(w)).get)
    }
  }

  def getBy(id: Long) : Future[Option[Weather]] = {
    Future.successful(list.find( item => item.id.contains(id)))
  }

  def create(weather: Weather) = {
    logger.info(s"Sending to create weather $weather")
    weatherCreateProducer.create(weather)
  }

  def update(weather: Weather) = {
    logger.info(s"Sending to update weather: $weather")
  }
}