package services

import akka.Done
import domain.{Board, BoardNotFound, BoardRequest, WeatherError, WeatherResult}
import javax.inject.Inject
import play.api.Logging
import repositories.BoardRepository

import scala.concurrent.{ExecutionContext, Future}

class BoardService @Inject() (boardRepository: BoardRepository)(implicit val ec: ExecutionContext) extends Logging {

  def remove(boardId:Long): Future[Either[BoardNotFound, Done]] = boardRepository.remove(boardId).map {
    case Right(_) => Right(Done)
    case Left(_) => Left(BoardNotFound(boardId))
  }

  def getAll(): WeatherResult[Seq[Board]] = boardRepository.getAll()

  def save(request: BoardRequest): WeatherResult[Board] = {
    val newBoard = Board(None, request.description)
    boardRepository.save(newBoard)
  }

  def getBy(boardId: Long): WeatherResult[Option[Board]] = {
    boardRepository.getBy(boardId)
  }
}
