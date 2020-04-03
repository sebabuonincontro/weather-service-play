package repositories

import domain.Board
import javax.inject.Inject
import play.api.Logging
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.statements.BoardStatement
import repositories.tables.Tables._
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class BoardRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider)
                                (implicit val ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]
  with Logging
  with BoardStatement {

  import profile.api._

  def remove(boardId: Long) = {
    db.run(delete(boardId)).map(_ > 0)
  }

  def save(newBoard: Board): Future[Either[Exception, Board]] =
    db.run(create(newBoard)).map( newId => Right(newBoard.copy(id = Some(newId))))

  def getAll(): Future[Seq[Board]] = db.run( boardsTable.result )

  def getBy(boardId: Long): Future[Option[Board]] = db.run(getById(boardId))

}
