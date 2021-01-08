package domain

import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

sealed trait WeatherError {
  val type_error: String
  val message: String
}
case class BoardNotFound(id: Long) extends WeatherError {
  override val type_error: String = "not_found"
  override val message: String = s"domain.Board not found for id: $id"
}

case class LocationNotFound(id: Long) extends WeatherError {
  override val type_error: String = "not_found"
  override val message: String = s"domain.Location not found for id: $id"
}

case class ForecastNotFound() extends WeatherError {
  override val type_error: String = "not_found"
  override val message: String = s"domain.Forecast not found."
}

case class YahooRequestLimitExceeded() extends WeatherError {
  override val type_error: String = "limit_error"
  override val message: String = s"Yahoo Request Limit Achieved."
}

case class YahooServiceError(error: String) extends WeatherError {
  override val type_error: String = "limit_error"
  override val message: String = s"Yahoo's service error: $error"
}

case class DataBaseError(error: String) extends WeatherError {
  override val type_error: String = "db_error"
  override val message: String = s"DB Error: $error"
}

case class StreamError(error: String) extends WeatherError {
  override val type_error: String = "stream_error"
  override val message: String = s"Stream Error: $error"
}
