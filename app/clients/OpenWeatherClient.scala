package clients

import domain.Implicits._
import domain._
import javax.inject.Inject
import play.api.Logging
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.{ExecutionContext, Future}

/**
 * Provides connection with Open Weather API Service
 * @param ws
 * @param openWeatherConfiguration
 * @param ec
 */
class OpenWeatherClient @Inject()(
    ws: WSClient,
    openWeatherConfiguration: OpenWeatherConfiguration)(implicit ec: ExecutionContext) extends Logging {

  /**
   * Get Data through location.
   * @param location location in string format
   * @return
   */
  def getCityDataBy(location: String): WeatherResult[LocationResponse] = {
    ws.url(openWeatherConfiguration.urlId)
      .withQueryStringParameters(
        "q" -> location,
        "appid" -> openWeatherConfiguration.apiKey)
      .get()
      .map { response: WSResponse =>
        response.status match {
          case 200 => response.json.validate[LocationResponse].fold(
            error => {
              logger.error(s"OpenWeather Service - location. $error")
              Left(YahooServiceError(s"OpenWeather - location. $error"))
            },
            body => Right(body)
          )
          case error => Left(YahooServiceError(s"Error when call OpenWeather Service. $error"))
        }
      }
  }

  /**
   * Get forecast weekly Info through latitude and longitude data.
   * @param latitude latitude spot.
   * @param longitude longitude spot.
   * @return Forecast weekly data.
   */
  def getForecastFor(latitude: Double, longitude: Double): WeatherResult[ForecastResponse] = {
    ws.url(openWeatherConfiguration.urlForecast)
      .withQueryStringParameters(
        "lat" -> latitude.toString,
        "lon" -> longitude.toString,
        "exclude" -> "current,minutely,hourly,alerts",
        "appid" -> openWeatherConfiguration.apiKey)
      .get()
      .map{ response: WSResponse =>
        response.status match {
          case 200 => response.json.validate[ForecastResponse].fold(
            error => {
              logger.error(s"OpenWeather - get Forecast. $error")
              Left(YahooServiceError(s"OpenWeather - get Forecast. $error"))
            },
            success => Right(success)
          )
        }
      }

  }
}
