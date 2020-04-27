package repositories.statements

import domain.BoardLocations
import slick.dbio.DBIO
import repositories.tables.Tables._
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext

trait BoardLocationStatement {

  def createNewBoardLocation(boardLocation: BoardLocations): DBIO[Int] =
    boardLocationTable.insertOrUpdate(boardLocation)

  def removeBoardLocation(boardId: Long, locationId: Long)(implicit ec: ExecutionContext): DBIO[Int] =
    boardLocationTable.filter(t => t.boardId === boardId && t.locationId === locationId).delete
}
