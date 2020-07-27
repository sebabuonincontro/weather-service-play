package domain

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

import play.api.libs.json._

object Implicits {

  implicit lazy val boardDecoder: Decoder[Board] = deriveDecoder[Board]
  implicit lazy val boardEncoder: Encoder[Board] = deriveEncoder[Board]

  implicit lazy val locationDecoder: Decoder[Location] = deriveDecoder[Location]
  implicit lazy val locationEncoder: Encoder[Location] = deriveEncoder[Location]

  implicit val placeReads = Json.reads[PlaceResponse]
  implicit val placeWrites = Json.writes[PlaceResponse]

  implicit val woeidReads = Json.reads[WoeidResponse]
  implicit val woeidWrites = Json.writes[WoeidResponse]

  implicit val conditionReads = Json.reads[ConditionResponse]
  implicit val conditionWrites = Json.writes[ConditionResponse]

  implicit val forecastReads = Json.reads[ForecastResponse]
  implicit val forecastWrites = Json.writes[ForecastResponse]

  implicit val itemReads = Json.reads[ItemResponse]
  implicit val itemWrites = Json.writes[ItemResponse]

  implicit val channelReads = Json.reads[ChannelResponse]
  implicit val channelWrites = Json.writes[ChannelResponse]

  implicit val resultReads = Json.reads[ResultResponse]
  implicit val resultWrites = Json.writes[ResultResponse]

  implicit val queryBodyReads = Json.reads[QueryBody[WoeidResponse]]
  implicit val queryBodyWrites = Json.writes[QueryBody[WoeidResponse]]

  implicit val queryResultReads = Json.reads[QueryBody[ResultResponse]]
  implicit val queryResultWrites = Json.writes[QueryBody[ResultResponse]]

  implicit val mainResultReads = Json.reads[MainBody[ResultResponse]]
  implicit val mainResultWrites = Json.writes[MainBody[ResultResponse]]

  implicit val mainBodyReads = Json.reads[MainBody[WoeidResponse]]
  implicit val mainBodyWrites = Json.writes[MainBody[WoeidResponse]]

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

}

