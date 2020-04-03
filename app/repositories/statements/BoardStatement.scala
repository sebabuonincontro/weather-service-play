package repositories.statements

import domain.Board
import repositories.tables.Tables._
import slick.jdbc.MySQLProfile.api._

trait BoardStatement {

  def getById(boardId: Long): DBIO[Option[Board]] = boardsTable.filter(_.id === boardId).result.headOption

  def create(newBoard: Board): DBIO[Long] = (boardsTable returning boardsTable.map(_.id)) += newBoard

  def delete(boardId: Long): DBIO[Int] = boardsTable.filter(_.id === boardId).delete
}
