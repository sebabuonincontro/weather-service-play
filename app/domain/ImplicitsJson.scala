package domain

import play.api.libs.json.Json

object ImplicitsJson {

  implicit val weatherReads = Json.reads[Weather]
  implicit val weatherWrites = Json.writes[Weather]

}
