package repositories.tables

import domain.Forecast
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

class ForecastTable(tag: Tag) extends Table[Forecast](tag, "forecasts") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def newsId = column[Long]("newsId")
  def woeid = column[String]("woeid")
  def date = column[String]("date")
  def high = column[Int]("high")
  def low = column[Int]("low")
  def forecast = column[String]("forecast")

  override def * = (id.?, newsId, woeid, date, high, low, forecast) <> (Forecast.tupled, Forecast.unapply)
}
