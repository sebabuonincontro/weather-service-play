package repositories.tables

import domain.Location
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

class LocationTable(tag: Tag) extends Table[Location](tag, "locations") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def woeid = column[Option[String]]("woeid")
  def location = column[String]("location")

  def * : ProvenShape[Location] = (id.?, woeid, location) <> ({
    case (id, woeid, location) => Location(id, woeid, location)
  }, {
    l: Location => Some((l.id, l.woeid, l.location))
  })
}
