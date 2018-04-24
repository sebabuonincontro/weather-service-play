package repository

import com.redis.RedisClient
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait RedisLike {

  val config = ConfigFactory.load()
  lazy val host = config.getString("db.redis.host")
  lazy val port = config.getInt( "db.redis.port")

  val redis = new RedisClient(host, port)


  def withLastId(key: String): Future[Long] = {
    doRedisFor{ redis =>
      redis.get(key) match {
        case Some(id) => {
          redis.set(key, id + 1L)
          id.toLong + 1L
        }
        case None => {
          redis.set(key, 1L)
          1L
        }
      }
    }
  }

  def doRedisFor[T](f: RedisClient => T) : Future[T] = {
    Future(f(redis))
      .recover{
        case error : Throwable => throw RedisException(error.getMessage)
      }
  }
}

case class RedisException(msg: String) extends Exception(msg)