package services

import com.google.inject.Inject
import domain.Weather
import exceptions.WeatherServiceException
import play.api.Configuration

import scala.concurrent.Future

class WeatherService @Inject() (configuration: Configuration) {

  val list = List(
    Weather(Some(1), "description1"),
    Weather(Some(2), "description2"),
    Weather(Some(3), "description3")
  )

  def getAll(): Future[List[Weather]] = {
    //Future.successful(list)
    Future.failed(WeatherServiceException("Error Grave"))
  }

  def getBy(id: Long) : Future[Option[Weather]] = {
    Future.successful(list.find( item => item.id.contains(id)))
  }
}