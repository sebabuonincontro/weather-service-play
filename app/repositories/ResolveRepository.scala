package repositories

import domain.{WeatherEither, WeatherException, WeatherResult}
import play.api.Logging
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

trait ResolveRepository extends HasDatabaseConfigProvider[JdbcProfile] { _ : Logging =>

  import profile.api._

  def resolve[T](actions: DBIOAction[T, NoStream, Nothing])
                (success: => T => WeatherEither[T])
                (implicit ec: ExecutionContext): WeatherResult[T] = {
    db.run(actions)
      .map(success)
      .recover{
        case e =>
          logger.error("Error while execute query", e)
          Left(new WeatherException(e.getMessage))
      }
  }
}
