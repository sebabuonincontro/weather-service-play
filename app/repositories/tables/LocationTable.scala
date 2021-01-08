package repositories.tables

import domain.Location
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

class LocationTable(tag: Tag) extends Table[Location](tag, "locations") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def latitude = column[Double]("latitude")
  def longitude = column[Double]("longitude")
  def location = column[String]("location")

  def * : ProvenShape[Location] = (id.?, latitude, longitude, location) <> ({
    case (id, latitude, longitude, location) => Location(id, latitude, longitude, location)
  }, {
    l: Location => Some((l.id, l.latitude, l.longitude, l.location))
  })
}
