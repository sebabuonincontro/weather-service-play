package services

import controllers.BoardController
import domain.{Location, WeatherException}
import javax.inject.Inject
import play.api.Logger
import repositories.{BoardLocationRepository, BoardRepository, LocationRepository}

import scala.concurrent.{ExecutionContext, Future}

class LocationService @Inject()(locationRepository: LocationRepository,
                                boardRepository: BoardRepository,
                                boardLocationRepository: BoardLocationRepository)
                               (implicit val ec: ExecutionContext){

  val logger = Logger(classOf[BoardController])

  def create(boardId: Long, location: Location): Future[Either[Exception, Location]] = {
    (for {
      newLocation <- locationRepository.save(boardId, location)
    } yield Right(newLocation)).recover {
      case e: Exception => Left(new WeatherException(e.getMessage))
    }
  }

}
