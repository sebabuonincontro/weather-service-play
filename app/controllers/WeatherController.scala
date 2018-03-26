package controllers

import javax.inject._

import actors.WeatherActor.CreateWeather
import akka.actor.ActorRef
import akka.util.Timeout
import com.google.inject.Singleton
import domain.Weather
import exceptions.WeatherServiceException
import play.api.libs.json.Json
import services.WeatherService

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import domain.ImplicitsJson._
import play.api.mvc.InjectedController

@Singleton
class WeatherController @Inject()(@Named("weather-actor") actor: ActorRef,
                                  weatherService: WeatherService)
                                 (implicit ec: ExecutionContext) extends InjectedController {

  implicit val timeout: Timeout = 5.seconds

  def getAll() = Action.async { request =>
    weatherService
      .getAll()
      .map { list =>
        Ok(Json.toJson(list))
      }
      .recover{
        case e: WeatherServiceException => InternalServerError(Json.toJson(e))
      }
  }

  def getBy(id: String) = Action.async { request =>
    weatherService
      .getBy(id.toLong)
      .map { value =>
        Ok(Json.toJson(value))
      }.recover{
        case e: WeatherServiceException => InternalServerError(Json.toJson(e))
      }
  }

  def create() = Action(parse.json) { request =>
    val weather = request.body.validate[Weather]

    weather.fold(
      error => InternalServerError("error"),
      newWeather => {
        actor ! CreateWeather(newWeather)
        Ok("Sending To create.")
      }
    )
  }

  def update() = Action(parse.json) { request =>
    val weather = request.body.validate[Weather]

    weather.fold(
      error => InternalServerError("error"),
      newWeather => {
        actor ! CreateWeather(newWeather)
        Ok("Sending To create.")
      }
    )
  }
}
