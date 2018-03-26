package actors

import javax.inject.Inject

import akka.actor.Actor
import domain.Weather
import services.WeatherService

object WeatherActor {
  case class CreateWeather(weather: Weather)
  case class UpdateWeather(weather: Weather)
}

class WeatherActor @Inject() (weatherService: WeatherService) extends Actor {
  import WeatherActor._

  override def receive = {
    case CreateWeather(weather) => weatherService.create(weather)
    case UpdateWeather(weather) => weatherService.update(weather)
  }
}
