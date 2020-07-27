package services

import java.sql.Timestamp

import akka.Done
import cats.data._
import cats.implicits._
import domain._
import javax.inject.Inject
import org.joda.time.DateTime
import repositories.NewsRepository

import scala.concurrent.ExecutionContext

/**
 * Provides functions to save news and forecast.
 * @param newsRepository [[NewsRepository]]
 * @param forecastService [[ForecastService]]
 * @param ec [[ExecutionContext]]
 */
class NewsService @Inject() (newsRepository: NewsRepository,
                             forecastService: ForecastService)(implicit ec: ExecutionContext){

  /**
   * Save news and forecasts.
   * @param wsResponse list of forecast to save.
   * @param woeid woeid from yahoo weather service.
   * @return [[Done]]
   */
  def saveNewsFrom(wsResponse: MainBody[ResultResponse], woeid: String): WeatherResult[Done] = {
    val condition = wsResponse.query.results.channel.item.condition
    val forecasts = wsResponse.query.results.channel.item.forecast.toList

    val news = News(None,
      woeid,
      new Timestamp(DateTime.now.getMillis),
      condition.date,
      condition.temp,
      condition.text)

    (for {
      persist <- EitherT( newsRepository.save(news) )
      _ <- EitherT( forecastService.save(forecasts, persist.id.get, woeid))
    } yield Done).value
  }
}
