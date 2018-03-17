package exceptions

import play.api.libs.json.Json

case class WeatherServiceException(msg: String) extends Exception

object WeatherServiceException{
  implicit val exceptionReads = Json.reads[WeatherServiceException]
  implicit val exceptionWrites = Json.writes[WeatherServiceException]
}
