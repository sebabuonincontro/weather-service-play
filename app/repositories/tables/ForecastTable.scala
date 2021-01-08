package repositories.tables

import java.sql.Timestamp
import java.time.LocalDateTime

import domain.Forecast
import slick.jdbc.MySQLProfile.api._

class ForecastTable(tag: Tag) extends Table[Forecast](tag, "forecasts") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def locationId = column[Long]("location_id")
  def datetime = column[LocalDateTime]("date_time")
  def tempMin = column[Double]("temp_min")
  def tempMax = column[Double]("temp_max")
  def humidity = column[Int]("humidity")
  def windSpeed = column[Double]("wind_speed")
  def description = column[String]("description")
  def clouds = column[Int]("clouds") //cloudiness %
  def pop = column[Double]("pop") //Probability of precipitation
  def rain = column[Option[Double]]("rain") //mm
  def snow = column[Option[Double]]("snow")  //mm

  override def * = (
    id.?,
    locationId,
    datetime,
    tempMin,
    tempMax,
    humidity,
    windSpeed,
    description,
    clouds,
    pop,
    rain,
    snow
  ) <> (Forecast.tupled, Forecast.unapply)
}
