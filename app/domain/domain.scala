package domain

import java.sql.Timestamp

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}


case class Board(
  id: Option[Long],
  description: String,
  locations: Seq[Location] = Seq())

object Board {
  implicit lazy val decoder: Decoder[Board] = deriveDecoder[Board]
  implicit lazy val encoder: Encoder[Board] = deriveEncoder[Board]
}

case class BoardRequest(description: String)

case class Location(
  id: Option[Long],
  woeid: Option[String],
  location: String)

object Location {

  implicit lazy val decoder: Decoder[Location] = deriveDecoder[Location]
  implicit lazy val encoder: Encoder[Location] = deriveEncoder[Location]
}

case class BoardLocations(
  boardId: Option[Long],
  locationId: Option[Long])

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
