package repositories.tables

import domain.Board
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

class BoardTable(tag:Tag) extends Table[Board](tag, "boards"){
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def description = column[String]("description")

  override def * : ProvenShape[Board] =
    (id.?, description) <> ({
      case (id, description) => Board(id, description)
    }, {
      b:Board => Some((b.id, b.description))
    })
}
