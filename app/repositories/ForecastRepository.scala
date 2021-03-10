package repositories

import com.google.inject.Inject
import domain.{Forecast, WeatherResult}
import play.api.Logging
import play.api.db.slick.DatabaseConfigProvider
import repositories.statements.ForecastStatement
import repositories.tables.Tables._
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext

/**
 * Persist Forecast Data.
 * @param dbConfigProvider Database Configuration Provider Injected by Play.
 * @param ec instance of [[ExecutionContext]]
 */
class ForecastRepository @Inject() () (override val dbConfigProvider: DatabaseConfigProvider)
                                   (implicit ec: ExecutionContext)
  extends Logging
    with ForecastStatement
    with ResolveRepository{

  def save(forecastList: List[Forecast]) = {
    forecastList.tail.foldLeft( resolve(forecastTable.insertOrUpdate(forecastList.head))(Right(_)) ){ (_, next) =>
      resolve(forecastTable.insertOrUpdate(next))(Right(_))
    }
  }

  def getForecastFor(locationId: Long): WeatherResult[Seq[Forecast]] = {
    resolve(getForecast(locationId))(Right(_))
  }
}
