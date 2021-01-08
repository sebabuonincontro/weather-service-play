package repositories.statements

import domain.Forecast
import repositories.tables.Tables._
import slick.jdbc.MySQLProfile.api._

trait ForecastStatement {

  def getForecast(locationId: Long): DBIO[Seq[Forecast]] = {
    forecastTable.filter(_.locationId === locationId).result
  }
}
