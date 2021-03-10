package controllers

import domain.{Location, LocationRequest}
import io.circe.generic.auto._
import io.circe.syntax._

import javax.inject.Inject
import play.api.{Logger, Logging}
import play.api.libs.circe.Circe
import play.api.libs.json.JsValue
import play.api.mvc.{Action, BaseController, ControllerComponents}
import services.LocationService

import scala.concurrent.ExecutionContext

class LocationController @Inject()(val controllerComponents: ControllerComponents,
                                   val locationService: LocationService)
                                  (implicit val ec: ExecutionContext) extends BaseController with Resolver with Circe with Logging {

  def save(boardId: Long) = Action.async(circe.json[LocationRequest]){ request =>
    logger.info(s"Create a Location for Board: ${boardId}")
    locationService.create(boardId, request.body) map (resolve(_) { newLocation =>
      logger.info(s"Location for id: ${newLocation.id.get} created.")
      Accepted(newLocation.asJson)
    })
  }

  def remove(boardId: Long, locationId: Long) = Action.async { _ =>
    locationService.remove(boardId, locationId) map( resolve(_) { _ =>
      Ok
    })
  }

  def getForecastBy(locationId: Long) = Action.async { _ =>
    locationService.getForecastBy(locationId) map( resolve(_) { forecast =>
      Accepted(forecast.asJson)
    })
  }
}
