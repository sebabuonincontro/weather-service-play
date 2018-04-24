package repository

import com.google.inject.Inject
import domain.Weather
import javax.inject.Singleton
import play.api.{Configuration, Logger}
import play.libs.Json

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class WeatherRepository @Inject() (configuration: Configuration) extends RedisLike {

  val logger = Logger(this.getClass)
  val keyId = "weather:id"
  val weatherKey = "weathers"

  def create(weather: Weather) = {
    withLastId(keyId) flatMap{ id =>
      doRedisFor{ redis =>
        redis.hset(weatherKey, id, Json.toJson(weather))
        logger.info(s"new weather created. $weather")
      }
    }
  }

  def getAll() : Future[Map[String, String]] = {
    doRedisFor{ redis =>
      redis.hgetall1(weatherKey) match {
        case Some(map) => map
        case None => Map()
      }
    }
  }
}
