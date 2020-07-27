package repositories.statements

import domain.News
import repositories.tables.Tables.newsTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext

trait NewsStatement {

  def saveNews(news: News)(implicit ec: ExecutionContext): DBIO[News] = {
    ((newsTable returning newsTable.map(_.id)) += news).map(newId => news.copy(id = Some(newId)))
  }
}
