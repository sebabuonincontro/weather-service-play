package repositories

import domain.{Board, WeatherResult}
import javax.inject.Inject
import play.api.Logging
import play.api.db.slick.DatabaseConfigProvider
import repositories.statements.{BoardStatement, LocationStatement}
import repositories.tables.Tables._

import scala.concurrent.ExecutionContext

class BoardRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider)
                                (implicit val ec: ExecutionContext)
  extends ResolveRepository
    with Logging
    with BoardStatement
    with LocationStatement {

  import profile.api._

  def remove(boardId: Long): WeatherResult[Int] =
    resolve(delete(boardId))(Right(_))

  def save(newBoard: Board): WeatherResult[Board] =
    resolve(create(newBoard))(Right(_))

  def getAll(): WeatherResult[Seq[Board]] =
    resolve( boardsTable.result )(Right(_))

  def getBy(boardId: Long): WeatherResult[Option[Board]] = {
    val query = for {
      board <- getById(boardId)
      locations <- getLocationsBy(boardId)
    } yield board.map(_.copy(locations = locations))

    resolve(query)(Right(_))
  }
}
