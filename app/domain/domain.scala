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

case class Location(
  id: Option[Long],
  woeid: Option[String],
  location: String) extends WeatherDomain

case class BoardLocations(
  boardId: Option[Long],
  locationId: Option[Long])

case class News(
  id: Option[Long],
  woeid: String,
  createDate: Timestamp,
  date: String,
  temp: String,
  condition: String) extends WeatherDomain

case class Forecast(
  id: Option[Long],
  newsId: Long,
  woeid: String,
  date: String,
  high: Int,
  low: Int,
  forecast: String) extends WeatherDomain

case class RequestLimit(
                         date: Timestamp,
                         quantity: Int)

//Entities used for the Rest service result
case class LocationWithNewsAndForecasts( location: Location, news: News, forecasts: Seq[Forecast])

case class YahooConfiguration(url: String, selectWoeid: String, selectForecast: String, limit: Int)

case class MainBody[T](query: QueryBody[T])

case class QueryBody[T](results: T)

case class WoeidResponse(place: PlaceResponse)

case class PlaceResponse(name: String, woeid: String)

case class ResultResponse(channel: ChannelResponse)

case class ChannelResponse(item: ItemResponse)

case class ItemResponse(
                         title: String,
                         condition: ConditionResponse,
                         forecast: Seq[ForecastResponse])

case class ConditionResponse(
                              code: String,
                              date: String,
                              temp: String,
                              text: String)

case class ForecastResponse(
                             code: String,
                             date: String,
                             day: String,
                             high: String,
                             low: String,
                             text: String)

