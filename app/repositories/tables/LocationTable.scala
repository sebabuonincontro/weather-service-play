package repositories.tables

import domain.Location
import slick.jdbc.MySQLProfile.api._

class LocationTable(tag: Tag) extends Table[Location](tag, "locations") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def woeid = column[Option[String]]("woeid")
  def location = column[String]("location")

  def * = (id.?, woeid, location) <> (Location.tupled, Location.unapply)
}
