package domain

import java.sql.Timestamp

case class Board(
  id: Option[Long],
  description: String)

case class Location(
  id: Option[Long],
  woeid: Option[String],
  location: String)

case class BoardLocations(
  boardId: Long,
  locationId: Long)

case class BoardWithLocations(
  board: Board,
  locations: Seq[Location])

case class News(
  id: Option[Long],
  woeid: String,
  createDate: Timestamp,
  date: String,
  temp: String,
  condition: String)

case class Forecast(
  id: Option[Long],
  newsId: Long,
  woeid: String,
  date: String,
  high: Int,
  low: Int,
  forecast: String)

case class RequestLimit(
                         date: Timestamp,
                         quantity: Int)

//Entities used for the Rest service result
case class LocationWithNewsAndForecasts( location: Location, news: News, forecasts: Seq[Forecast])
