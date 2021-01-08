package controllers

import io.circe.syntax.EncoderOps
import javax.inject.Inject
import play.api.Logging
import play.api.libs.circe.Circe
import play.api.mvc.{BaseController, ControllerComponents}
import services.StreamService

import scala.concurrent.ExecutionContext

class StreamController @Inject()(val controllerComponents: ControllerComponents,
                                 val streamService: StreamService)
                                (implicit val ec: ExecutionContext)
  extends BaseController
    with Circe
    with Resolver
    with Logging {

  def execute() = Action.async { _ =>
    streamService.run().map( resolve(_) { _ =>
      logger.info("Stream processed OK")
      Ok(Map("message" -> "Stream processed OK").asJson)
    })
  }
}
