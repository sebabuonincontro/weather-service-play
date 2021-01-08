package repositories

import domain.{BoardLocations, Forecast, Location, WeatherResult}
import javax.inject.Inject
import play.api.Logging
import play.api.db.slick.DatabaseConfigProvider
import repositories.statements.{BoardLocationStatement, BoardStatement, LocationStatement}

import scala.concurrent.ExecutionContext

case class LocationRepository @Inject()(override val dbConfigProvider: DatabaseConfigProvider)
                                       (implicit ec: ExecutionContext)
  extends Logging
    with ResolveRepository
    with LocationStatement
    with BoardStatement
    with BoardLocationStatement {

  def save(boardId: Long, location: Location): WeatherResult[Location] = {
    val sequence = for {
      board <- getById(boardId)
      locationId <- createNewLocation(location)
      _ <- createNewBoardLocation(BoardLocations(board.flatMap(_.id), locationId))
    } yield location.copy(id = locationId)

    resolve(sequence)(Right(_))
  }

  def remove(boardId: Long, locationId: Long): WeatherResult[Unit] = {
    val sequence = for {
      _ <- removeLocation(locationId)
      _ <- removeBoardLocation(boardId, locationId)
    } yield()

    resolve(sequence)(Right(_))
  }

}
