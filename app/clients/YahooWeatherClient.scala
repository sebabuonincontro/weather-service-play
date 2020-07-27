package clients

import domain.Implicits._
import domain._
import javax.inject.Inject
import play.api.Logging
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.{ExecutionContext, Future}

/**
 * Provides connection with Yahoo Weather Service
 * @param ws
 * @param yahooConfiguration
 * @param ec
 */
class YahooWeatherClient @Inject()(ws: WSClient, yahooConfiguration: YahooConfiguration)(implicit ec: ExecutionContext) extends Logging {

  /**
   * Get Woeid info through location.
   * @param location location in string format
   * @return
   */
  def getWoeidBy(location: String): WeatherResult[MainBody[WoeidResponse]] = {
    val query = yahooConfiguration.selectWoeid + location + "'"
    ws.url(yahooConfiguration.url)
      .withQueryStringParameters("q" -> query, "format" -> "json")
      .get()
      .map { response: WSResponse =>
        response.status match {
          case 200 => response.json.validate[MainBody[WoeidResponse]].fold(
            error => {
              logger.error(s"Yahoo Service - get Woeid. $error")
              Left(YahooServiceError(s"Yahoo Service - get Woeid. $error"))
            },
            body => Right(body)
          )
          case error => Left(YahooServiceError(s"Error when call yahoo service. $error"))
        }
      }
  }

  /**
   * Get forecast Info through woeid id.
   * @param woeid
   * @return
   */
  def getForecastFor(woeid: String): WeatherResult[MainBody[ResultResponse]] = {
    val query = yahooConfiguration.selectForecast + woeid + "'"

    ws.url(yahooConfiguration.url)
      .withQueryStringParameters("q" -> query, "format" -> "json")
      .get()
      .map{ response: WSResponse =>
        response.status match {
          case 200 => response.json.validate[MainBody[ResultResponse]].fold(
            error => {
              logger.error(s"Yahoo Service - get Forecast. $error")
              Left(YahooServiceError(s"Yahoo Service - get Forecast. $error"))
            },
            success => Right(success)
          )
        }
      }

  }
}
