package repositories.statements

import domain.Location
import repositories.tables.Tables.locationTable
import slick.jdbc.MySQLProfile.api._

trait LocationStatement {

  def createNewLocation(location: Location): DBIO[Long] = locationTable returning locationTable.map(_.id) += location
}
