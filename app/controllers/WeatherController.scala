package controllers

import javax.inject.Inject

import com.google.inject.Singleton
import exceptions.WeatherServiceException
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.WeatherService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class WeatherController @Inject()(cc: ControllerComponents,
                                  weatherService: WeatherService) extends AbstractController(cc) {

  def getAll() = Action.async { implicit request =>
    weatherService
      .getAll()
      .map { list =>
        Ok(Json.toJson(list))
      }
      .recover{
        case e: WeatherServiceException => InternalServerError(Json.toJson(e))
      }
  }

  def getBy(id: String) = Action.async { implicit request =>
    weatherService
      .getBy(id.toLong)
      .map { value =>
        Ok(Json.toJson(value))
      }
  }
}
