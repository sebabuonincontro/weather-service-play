package domain

import io.circe.generic.semiauto._
import io.circe.java8.time.{JavaTimeDecoders, JavaTimeEncoders}
import io.circe.{Decoder, Encoder}

object Implicits extends JavaTimeEncoders
  with JavaTimeDecoders {

  implicit lazy val boardRequestDecoder: Decoder[BoardRequest] = deriveDecoder[BoardRequest]
  implicit lazy val boardRequestEncoder: Encoder[BoardRequest] = deriveEncoder[BoardRequest]

  implicit lazy val boardDecoder: Decoder[Board] = deriveDecoder[Board]
  implicit lazy val boardEncoder: Encoder[Board] = deriveEncoder[Board]

  implicit lazy val locationRequestDecoder: Decoder[LocationRequest] = deriveDecoder[LocationRequest]
  implicit lazy val locationRequestEncoder: Encoder[LocationRequest] = deriveEncoder[LocationRequest]

  implicit lazy val locationDecoder: Decoder[Location] = deriveDecoder[Location]
  implicit lazy val locationEncoder: Encoder[Location] = deriveEncoder[Location]

  implicit val tempReads: Decoder[DailyTemperature] = deriveDecoder[DailyTemperature]
  implicit val tempWrites: Encoder[DailyTemperature] = deriveEncoder[DailyTemperature]

//  implicit val weatherReads: Decoder[DailyWeather] = deriveDecoder[DailyWeather]
//  implicit val weatherWrites: Encoder[DailyWeather] = deriveEncoder[DailyWeather]

  implicit val dailyReads: Decoder[DailyForecast] = deriveDecoder[DailyForecast]
  implicit val dailyWrites: Encoder[DailyForecast] = deriveEncoder[DailyForecast]

  implicit val coordsEncoder: Decoder[Coords] = deriveDecoder[Coords]
  implicit val coordsDecoder: Encoder[Coords] = deriveEncoder[Coords]

  implicit val forecastDecoder: Decoder[ForecastResponse] = deriveDecoder[ForecastResponse]
  implicit val forecastEncoder: Encoder[ForecastResponse] = deriveEncoder[ForecastResponse]

  implicit val locationResponseReads = deriveDecoder[LocationResponse]
  implicit val locationResponseWrites = deriveEncoder[LocationResponse]

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

