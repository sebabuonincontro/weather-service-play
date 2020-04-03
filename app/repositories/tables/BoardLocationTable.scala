package repositories.tables

import domain.BoardLocations
import slick.jdbc.MySQLProfile.api._

class BoardLocationTable(tag: Tag) extends Table[BoardLocations](tag, "board_locations"){
  def boardId = column[Long]("board_id", O.PrimaryKey)
  def locationId = column[Long]("location_id", O.PrimaryKey)

  def * = (boardId, locationId) <> (BoardLocations.tupled, BoardLocations.unapply)
}
