package repositories

import akka.io.IO
import domain.{BoardLocations, Location}
import javax.inject.Inject
import play.api.Logging
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.statements.{BoardStatement, LocationStatement}
import repositories.tables.BoardLocationStatement
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

case class LocationRepository @Inject()(val dbConfigProvider: DatabaseConfigProvider)
                                       (implicit val ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]
    with Logging
    with LocationStatement
    with BoardStatement
    with BoardLocationStatement {

  def save(boardId: Long, location: Location): Future[Location] = {
    val sequence = for {
      _ <- IO{ logger.info("")}
      board <- getById(boardId)
      locationId <- createNewLocation(location)
      _ <- createNewBoardLocation(BoardLocations(board.flatMap(_.id).getOrElse(0), locationId))
    } yield location.copy(id = Some(locationId))

    db.run(sequence)
  }
}
