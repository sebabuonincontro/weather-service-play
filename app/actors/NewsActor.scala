package actors

import actors.NewsActor.{GetNewsFor, WeatherMessage}
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import cats.data.EitherT
import cats.implicits._
import clients.YahooWeatherClient
import javax.inject.Inject
import services.NewsService

import scala.concurrent.ExecutionContext

/**
 * Actor Forecast Manager. It allows to run searching of new forecast from woeid in background.
 * @param yahooWeatherClient
 * @param newsService
 * @param executionContext
 */
class NewsActor @Inject() (yahooWeatherClient: YahooWeatherClient,
                           newsService: NewsService)(implicit val executionContext: ExecutionContext){

  val behavior: Behavior[WeatherMessage] = Behaviors.receiveMessage[WeatherMessage]{
    case GetNewsFor(woeid) =>
      for {
        forecasts <- EitherT( yahooWeatherClient.getForecastFor(woeid) )
        _ <- EitherT( newsService.saveNewsFrom(forecasts, woeid) )
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
  case class GetNewsFor(woeid: String) extends WeatherMessage
}
