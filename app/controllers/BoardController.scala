package controllers

import domain.Board
import io.circe.generic.auto._
import io.circe.syntax._
import javax.inject.Inject
import play.api.{Logger, Logging}
import play.api.libs.circe.Circe
import play.api.mvc.{BaseController, ControllerComponents}
import services.BoardService

import scala.concurrent.ExecutionContext

class BoardController @Inject()(val controllerComponents: ControllerComponents,
                                val boardService: BoardService)
                               (implicit val ec: ExecutionContext) extends BaseController with Resolver with Circe with Logging {

  def create() = Action.async(circe.json[Board]) { request =>
    logger.info(s"Creating new Board ${request.body.toString}")
    boardService.save(request.body).map (resolve(_) { result =>
      logger.info(s"Board ${result.id} created.")
      Created(result.asJson)
    })
  }

  def getAll() = Action.async { _ =>
    logger.info("Get All Boards")
    boardService.getAll().map (resolve(_) { result =>
      Ok(result.asJson)
    })
  }

  def remove(id: Long) = Action.async(circe.json[Board]) { _ =>
    logger.info(s"Remove Board with id: ${id}")
    boardService.remove(id).map (resolve(_) { _ =>
      logger.info(s"Board with id: ${id} removed.")
      Accepted(id.asJson)
    })
  }
}
