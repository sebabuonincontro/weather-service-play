package domain

import java.sql.Timestamp

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.java8.time.{JavaTimeDecoders, JavaTimeEncoders}
import play.api.libs.json.Json._
import play.api.libs.json._

object Implicits extends JavaTimeEncoders
  with JavaTimeDecoders {

  implicit lazy val boardRequestDecoder: Decoder[BoardRequest] = deriveDecoder[BoardRequest]
  implicit lazy val boardRequestEncoder: Encoder[BoardRequest] = deriveEncoder[BoardRequest]

  implicit lazy val boardDecoder: Decoder[Board] = deriveDecoder[Board]
  implicit lazy val boardEncoder: Encoder[Board] = deriveEncoder[Board]

  implicit lazy val locationDecoder: Decoder[Location] = deriveDecoder[Location]
  implicit lazy val locationEncoder: Encoder[Location] = deriveEncoder[Location]

  implicit val tempReads = Json.reads[DailyTemperature]
  implicit val tempWrites = Json.writes[DailyTemperature]

  implicit val weatherReads = Json.reads[DailyWeather]
  implicit val weatherWrites = Json.writes[DailyWeather]

  implicit val dailyReads = Json.reads[DailyForecast]
  implicit val dailyWrites = Json.writes[DailyForecast]

  implicit val forecastReads = Json.reads[ForecastResponse]
  implicit val forecastWrites = Json.writes[ForecastResponse]

  implicit val coordsReads = Json.reads[Coords]
  implicit val coordsWrites = Json.writes[Coords]

  implicit val locationResponseReads = Json.reads[LocationResponse]
  implicit val locationResponseWrites = Json.writes[LocationResponse]

  /**
   * Errors
   */
  implicit val errorDecoder: Decoder[WeatherError] = deriveDecoder[WeatherError]
  implicit val errorEncoder: Encoder[WeatherError] = deriveEncoder[WeatherError]

  implicit val bnfDecoder: Decoder[BoardNotFound] = deriveDecoder[BoardNotFound]
  implicit val bnfEncoder: Encoder[BoardNotFound] = deriveEncoder[BoardNotFound]

  implicit val lnfDecoder: Decoder[LocationNotFound] = deriveDecoder[LocationNotFound]
  implicit val lnfEncoder: Encoder[LocationNotFound] = deriveEncoder[LocationNotFound]

  implicit val fnfDecoder: Decoder[ForecastNotFound] = deriveDecoder[ForecastNotFound]
  implicit val fnfEncoder: Encoder[ForecastNotFound] = deriveEncoder[ForecastNotFound]

  implicit val yrDecoder: Decoder[YahooRequestLimitExceeded] = deriveDecoder[YahooRequestLimitExceeded]
  implicit val yrEncoder: Encoder[YahooRequestLimitExceeded] = deriveEncoder[YahooRequestLimitExceeded]

  implicit val ysrDecoder: Decoder[YahooServiceError] = deriveDecoder[YahooServiceError]
  implicit val ysrEncoder: Encoder[YahooServiceError] = deriveEncoder[YahooServiceError]

  implicit val dbeDecoder: Decoder[DataBaseError] = deriveDecoder[DataBaseError]
  implicit val dbeEncoder: Encoder[DataBaseError] = deriveEncoder[DataBaseError]

  implicit val streamDecoder: Decoder[StreamError] = deriveDecoder[StreamError]
  implicit val streamEncoder: Encoder[StreamError] = deriveEncoder[StreamError]
}

