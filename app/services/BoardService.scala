package services

import akka.Done
import domain.{Board, BoardNotFound, WeatherException}
import javax.inject.Inject
import play.api.Logger
import repositories.BoardRepository

import scala.concurrent.{ExecutionContext, Future}

class BoardService @Inject() (boardRepository: BoardRepository)(implicit val ec: ExecutionContext) {

  val logger = Logger(classOf[BoardService])

  def remove(boardId:Long): Future[Either[BoardNotFound, Done]] = boardRepository.remove(boardId).map {
    case true => Right(Done)
    case false => Left(BoardNotFound(boardId))
  }

  def getAll(): Future[Either[WeatherException, Seq[Board]]] = boardRepository.getAll().map(Right(_)).recover {
    case _: Throwable => Left(BoardNotFound(0))
  }


  def save(newBoard: Board): Future[Either[Exception, Board]] =
    boardRepository.save(newBoard).recover {
      case e: Exception =>
        logger.error(e.getMessage)
        Left(new WeatherException(e.getMessage))
    }
}
