package services

import actors.NewsActor.{GetNewsFor, WeatherMessage}
import akka.Done
import akka.actor.typed.ActorRef
import akka.actor.typed.ActorRef._
import cats.data._
import cats.implicits._
import clients.YahooWeatherClient
import domain.{Location, WeatherResult}
import javax.inject.Inject
import play.api.Logging
import repositories.LocationRepository

import scala.concurrent.ExecutionContext

/**
 * Provides functionality to create, update a remove any location.
 * @param locationRepository [[LocationRepository]]
 * @param yahooWeatherClient [[YahooWeatherClient]]
 * @param ec [[ExecutionContext]]
 */
class LocationService @Inject()(locationRepository: LocationRepository,
                                yahooWeatherClient: YahooWeatherClient,
                                actor: ActorRef[WeatherMessage])
                               (implicit val ec: ExecutionContext) extends Logging {


  def create(boardId: Long, location: Location): WeatherResult[Location] = {
    logger.info(s"Creating new location $location")
    val result = for {
      updated <- EitherT( getDataFromYahoo(location) )
      newLocation <- EitherT( locationRepository.save(boardId, updated))
      _ <- EitherT( lookForNews(newLocation.woeid.get))
    } yield newLocation

    result.value
  }

  private def lookForNews(woeid: String): WeatherResult[Done] = {
    actor ! GetNewsFor(woeid)
    WeatherResult(Done)
  }

  private def getDataFromYahoo(location: Location): WeatherResult[Location] = {
    logger.info(s"get data from yahoo for ${location.location}")
    (for {
      response <- EitherT( yahooWeatherClient.getWoeidBy(location.location) )
        place = response.query.results.place
      updated <- EitherT(WeatherResult(location.copy(woeid = Some(place.woeid), location = place.name)))
    } yield updated).value
  }

  def remove(boardId: Long, locationId: Long): WeatherResult[Unit] = {
    locationRepository.remove(boardId, locationId)
  }
}
