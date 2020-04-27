package controllers

import domain.{Board, BoardRequest}
import javax.inject.Inject
import play.api.Logging
import play.api.libs.circe.Circe
import play.api.mvc.{BaseController, ControllerComponents}
import services.BoardService
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.concurrent.ExecutionContext

class BoardController @Inject()(val controllerComponents: ControllerComponents,
                                val boardService: BoardService)
                               (implicit val ec: ExecutionContext) extends BaseController with Resolver with Circe with Logging {

  def create() = Action.async(circe.json[BoardRequest]) { request =>
    logger.info(s"Creating new Board ${request.body.toString}")
    boardService.save(request.body).map (resolve(_) { result =>
      logger.info(s"Board ${result.id} created.")
      Created(result.asJson)
    })
  }

  def getAll() = Action.async { _ =>
    logger.info("Get All Boards")
    boardService.getAll().map (resolve(_) { result =>
      logger.info(s"Boards found : ${result.size}")
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

  def getBy(boardId: Long) = Action.async { _ =>
    logger.info(s"Get Board and Locations by: $boardId")
    boardService.getBy(boardId).map( resolve(_) { boardWithLocations =>
      Ok(boardWithLocations.asJson)
    })
  }
}
