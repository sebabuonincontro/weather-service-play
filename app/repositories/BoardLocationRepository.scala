package repositories

import domain.{BoardLocations, BoardNotFound}
import javax.inject.Inject
import play.api.Logging
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.tables.Tables._
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class BoardLocationRepository @Inject()(val dbConfigProvider: DatabaseConfigProvider)
                                       (implicit val ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]
  with Logging{

  import profile.api._

  def save(boardId: Option[Long], locationId: Option[Long]): Future[BoardLocations] = {
    (boardId, locationId) match {
      case (Some(bid), Some(lid)) =>
        val boardLocation = BoardLocations (bid, lid)
        db.run(boardsLocationTable returning boardsLocationTable += boardLocation)
      case _ => Future.failed(new BoardNotFound(0))
    }
  }
}
