package services

import actors.NewsActor.{GetNewsFor, WeatherMessage}
import akka.Done
import akka.actor.typed.ActorRef
import akka.actor.typed.ActorRef._
import cats.data._
import cats.implicits._
import clients.OpenWeatherClient
import domain.{Forecast, Location, LocationRequest, WeatherResult}
import play.api.Logging
import repositories.{ForecastRepository, LocationRepository}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

/**
 * Provides functionality to create, update a remove any location.
 *
 * @param locationRepository as instance of [[LocationRepository]]
 * @param openWeatherClient  as intance of [[OpenWeatherClient]]
 * @param ec                 [[ExecutionContext]]
 */
class LocationService @Inject()(locationRepository: LocationRepository,
                                forecastRepository: ForecastRepository,
                                openWeatherClient: OpenWeatherClient,
                                actor: ActorRef[WeatherMessage])
                               (implicit val ec: ExecutionContext) extends Logging {

  /**
   * Create a new Location from a BoardId. Also, calls openWeatherService to obtain location data.
   *
   * @param boardId  board id
   * @param location new location
   * @return an instance of new location
   */
  def create(boardId: Long, location: LocationRequest): WeatherResult[Location] = {
    logger.info(s"Creating new location $location")
    val result = for {
      updated <- EitherT(searchLocation(location))
      newLocation <- EitherT(locationRepository.save(boardId, updated))
      _ <- EitherT(searchForNews(newLocation.id, newLocation.latitude, newLocation.longitude))
    } yield newLocation

    result.value
  }

  /**
   * sends message of weather actor so that it looks new forecast about location.
   *
   * @param latitude  latitude of location
   * @param longitude longitude of location
   * @return Done when it finishes
   */
  private def searchForNews(locationId: Option[Long], latitude: BigDecimal, longitude: BigDecimal): WeatherResult[Done] = {
    locationId match {
      case Some(id) =>
        actor ! GetNewsFor(id, latitude, longitude)
        WeatherResult(Done)
      case _ =>
        logger.error("Latitude and Longitude not found.")
        WeatherResult(Done)
    }
  }

  /**
   * calls OpenWeatherClient so as to get latitude and longitude data from current location.
   *
   * @param location the current location.
   * @return a copy of location object with latitude and longitude data.
   */
  private def searchLocation(location: LocationRequest): WeatherResult[Location] = {
    logger.info(s"get data from yahoo for ${location.location}")
    (for {
      response <- EitherT(openWeatherClient.getCityDataBy(location.location))
      updated <- EitherT(WeatherResult(Location(
        location = location.location,
        latitude = response.coord.lat,
        longitude = response.coord.lon
      )))
    } yield updated).value
  }

  def remove(boardId: Long, locationId: Long): WeatherResult[Unit] = {
    locationRepository.remove(boardId, locationId)
  }

  def getForecastBy(locationId: Long): WeatherResult[Seq[Forecast]] = {
    forecastRepository.getForecastFor(locationId)
  }

}
