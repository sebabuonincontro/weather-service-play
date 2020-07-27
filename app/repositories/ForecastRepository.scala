package repositories

import akka.Done
import com.google.inject.Inject
import domain.{Forecast, ForecastResponse, WeatherResult}
import play.api.Logging
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext
import tables.Tables._
import slick.jdbc.MySQLProfile.api._

class ForecastRepository @Inject() () (override val dbConfigProvider: DatabaseConfigProvider)
                                   (implicit ec: ExecutionContext)
  extends Logging
    with ResolveRepository{

  def save(forecastList: List[Forecast]) = {
    resolve(forecastTable returning forecastTable ++= forecastList)(Right(_))
  }

}
