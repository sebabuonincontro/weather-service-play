package repositories

import domain.News
import javax.inject.Inject
import play.api.Logging
import play.api.db.slick.DatabaseConfigProvider
import repositories.statements.NewsStatement

import scala.concurrent.ExecutionContext

class NewsRepository @Inject() (override val dbConfigProvider: DatabaseConfigProvider)
                               (implicit ec: ExecutionContext)
  extends Logging
  with ResolveRepository
  with NewsStatement {

  def save(news: News) = {
    resolve(saveNews(news))(Right(_))
  }
}
