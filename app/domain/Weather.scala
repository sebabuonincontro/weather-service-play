package domain

import play.api.libs.json.Json

case class Weather (id: Option[Long], description: String)

object Weather {
  implicit val weatherReads = Json.reads[Weather]
  implicit val weatherWrites = Json.writes[Weather]
}