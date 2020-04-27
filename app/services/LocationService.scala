package services

import domain.{Location, WeatherResult}
import javax.inject.Inject
import play.api.Logging
import repositories.LocationRepository

import scala.concurrent.ExecutionContext

class LocationService @Inject()(locationRepository: LocationRepository)
                               (implicit val ec: ExecutionContext) extends Logging {

  def create(boardId: Long, location: Location): WeatherResult[Location] =
    locationRepository.save(boardId, location)

  def remove(boardId: Long, locationId: Long): WeatherResult[Unit] = {
    locationRepository.remove(boardId, locationId)
  }
}
