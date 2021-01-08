package domain

import java.sql.Timestamp
import java.time.LocalDateTime

sealed trait WeatherDomain {
  val id: Option[Long]
}

case class Board(
  id: Option[Long],
  description: String,
  locations: Seq[Location] = Seq()) extends WeatherDomain

case class BoardRequest(description: String)

case class Location(
  id: Option[Long],
  latitude: Double,
  longitude: Double,
  location: String) extends WeatherDomain

case class BoardLocations(
  boardId: Option[Long],
  locationId: Option[Long])

case class Forecast(
  id: Option[Long],
  locationId: Long,
  datetime: LocalDateTime,
  tempMin: Double,
  tempMax: Double,
  humidity: Int,
  windSpeed: Double,
  description: String,
  clouds: Int, //cloudiness %
  pop: Double, //Probability of precipitation
  rain: Option[Double], //mm
  snow: Option[Double]  //mm
) extends WeatherDomain

case class RequestLimit(
                         date: Timestamp,
                         quantity: Int)

case class OpenWeatherConfiguration(apiKey: String, urlId: String, urlForecast: String, limit: Int)

case class LocationResponse(coord: Coords)

case class Coords(lat: Double, lon: Double)

case class ForecastResponse(daily: Seq[DailyForecast])

case class DailyForecast(
  dt: LocalDateTime,
  temp: DailyTemperature,
  humidity: Int,
  wind_speed: Double,
  weather: DailyWeather,
  clouds: Int,
  pop: Double,
  rain: Double,
  snow: Double
)

case class DailyWeather(
  main: String,
  description: String
)

case class DailyTemperature(
  min: Double,
  max: Double)

