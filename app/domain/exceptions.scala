package domain

class WeatherException(message: String) extends RuntimeException(message)
case class BoardNotFound(id: Long) extends WeatherException(s"domain.Board didn't find for id: $id")
case class LocationNotFound(id: Long) extends WeatherException(s"domain.Location didn't find for id: $id")
case class ForecastNotFound(id: Long) extends WeatherException(s"domain.Forecast didn't find for id: $id")
case class YahooRequestLimitExceeded() extends WeatherException(s"Yahoo Request Limit Achieved.")

