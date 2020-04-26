package repositories

import domain.{WeatherException, WeatherResult}
import javax.inject.Inject
import play.api.Logging
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

class BaseRepository @Inject()(override val dbConfigProvider: DatabaseConfigProvider)
                              (implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]
    with Logging {

  import profile.api._

  def run[T](result: DBIOAction[T, NoStream, Nothing]): WeatherResult[T] =
    db.run(result)
      .map(Right(_))
      .recover {
        case e =>
          logger.error("Error while execute query", e)
          Left(WeatherException(e.getMessage))
      }
}
