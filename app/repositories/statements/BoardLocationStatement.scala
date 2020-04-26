package repositories.statements

import domain.BoardLocations

trait BoardLocationStatement {

  def createNewBoardLocation(boardLocation: BoardLocations): DBIO[BoardLocations] =
    boardsLocationTable returning boardsLocationTable += boardLocation
}
