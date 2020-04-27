package repositories.statements

import akka.Done
import domain.Location
import repositories.tables.Tables._
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext

trait LocationStatement {

  def createNewLocation(newLocation: Location)(implicit ex: ExecutionContext): DBIO[Option[Long]] =
    locationTable.insertOrUpdate(newLocation) >> locationTable.map(_.id).max.result

  def getLocationsBy(boardId: Long)(implicit ec: ExecutionContext): DBIO[Seq[Location]] =
    (boardLocationTable filter(_.boardId === boardId) join
      locationTable on(_.locationId === _.id)).result.map(_.map(_._2))

  def removeLocation(locationId: Long)(implicit ec: ExecutionContext): DBIO[Boolean] =
    locationTable.filter(_.id === locationId).delete.map(_ > 0)
}
