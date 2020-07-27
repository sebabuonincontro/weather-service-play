package repositories.tables

import java.sql.Timestamp

import domain.News
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

class NewsTable(tag: Tag) extends Table[News](tag, "news") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def woeid = column[String]("woeid")
  def createDate = column[Timestamp]("create_date")
  def date = column[String]("date")
  def temp = column[String]("temp")
  def condition = column[String]("condition")

  override def * = (id.?, woeid, createDate, date, temp, condition) <> (News.tupled, News.unapply)
}
