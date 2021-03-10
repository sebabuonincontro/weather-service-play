package domain

import java.sql.Timestamp

sealed trait WeatherDomain {
  val id: Option[Long]
}

case class Board(
                  id: Option[Long],
                  description: String,
                  locations: Seq[Location] = Seq()) extends WeatherDomain

case class BoardRequest(description: String)

case class LocationRequest(
                            id: Option[Long],
                            location: String)

case class Location(
                     id: Option[Long] = None,
                     latitude: BigDecimal,
                     longitude: BigDecimal,
                     location: String) extends WeatherDomain

case class BoardLocations(
                           boardId: Option[Long],
                           locationId: Option[Long])

case class Forecast(
                     id: Option[Long],
                     locationId: Long,
                     tempMin: Double,
                     tempMax: Double,
                     humidity: Int,
                     windSpeed: Double,
                     description: String,
                     clouds: Int, //cloudiness %
                     pop: Double, //Probability of precipitation
                     rain: Option[Double], //mm
                     snow: Option[Double] //mm
                   ) extends WeatherDomain

case class RequestLimit(
                         date: Timestamp,
                         quantity: Int)

case class OpenWeatherConfiguration(apiKey: String, urlId: String, urlForecast: String, limit: Int)

case class LocationResponse(coord: Coords)

case class Coords(lat: BigDecimal, lon: BigDecimal)

case class ForecastResponse(
                             lat: BigDecimal,
                             lon: BigDecimal,
                             timezone: String,
                             timezone_offset: Long,
                             daily: Seq[DailyForecast])

case class DailyForecast(
                          temp: DailyTemperature,
                          humidity: Int,
                          wind_speed: Double,
                          clouds: Int,
                          pop: Double,
                          rain: Option[Double],
                          snow: Option[Double]
                        )

case class DailyTemperature(
                             min: Double,
                             max: Double)

