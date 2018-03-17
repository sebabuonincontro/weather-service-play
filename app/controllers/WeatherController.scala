package controllers

import javax.inject.Inject

import com.google.inject.Singleton
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.libs.json.Json
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
  }

  def getBy(id: String) = Action.async { implicit request =>
    weatherService
      .getBy(id.toLong)
      .map { value =>
        Ok(Json.toJson(value))
      }
  }
}
