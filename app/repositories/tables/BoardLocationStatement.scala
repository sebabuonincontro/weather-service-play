package repositories.tables

import domain.BoardLocations
import repositories.tables.Tables.boardsLocationTable
import slick.jdbc.MySQLProfile.api._

trait BoardLocationStatement {

  def createNewBoardLocation(boardLocation: BoardLocations): DBIO[BoardLocations] =
    boardsLocationTable returning boardsLocationTable += boardLocation
}
