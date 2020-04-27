package repositories.statements

import domain.Board
import repositories.tables.Tables._
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext

trait BoardStatement {

  def getById(boardId: Long): DBIO[Option[Board]] = boardsTable.filter(_.id === boardId).result.headOption

  def create(newBoard: Board)(implicit ec: ExecutionContext): DBIO[Board] =
    ((boardsTable returning boardsTable.map(_.id)) += newBoard).map(newId => newBoard.copy(id = Some(newId)))

  def delete(boardId: Long): DBIO[Int] = boardsTable.filter(_.id === boardId).delete
}
