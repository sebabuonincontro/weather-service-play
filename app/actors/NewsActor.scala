package actors

import actors.NewsActor.{GetNewsFor, WeatherMessage}
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import cats.data.EitherT
import cats.implicits._
import clients.OpenWeatherClient
import javax.inject.Inject
import services.ForecastService

import scala.concurrent.ExecutionContext

/**
 * Actor Forecast Manager. It allows to run searching of new forecast from woeid in background.
 * @param openWeatherClient injected instance of [[OpenWeatherClient]]
 * @param forecastService injected instance of [[ForecastService]]
 * @param executionContext instance of [[ExecutionContext]]
 */
class NewsActor @Inject() (openWeatherClient: OpenWeatherClient,
                           forecastService: ForecastService)(implicit val executionContext: ExecutionContext){

  val behavior: Behavior[WeatherMessage] = Behaviors.receiveMessage[WeatherMessage]{
    case GetNewsFor(locationId, latitude, longitude) =>
      for {
        forecasts <- EitherT( openWeatherClient.getForecastFor(latitude, longitude) )
        _ <- EitherT( forecastService.save(forecasts.daily.toList, locationId))
      } yield ()
      Behaviors.same
  }
}

/**
 * Companion Object for [[actors.NewsActor]] class.
 */
object NewsActor {
  val name = "news-actor"

  sealed trait WeatherMessage
  case class GetNewsFor(locationId: Long, latitude: Double, longitude: Double) extends WeatherMessage
}
