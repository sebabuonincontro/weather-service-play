import scala.concurrent.Future

package object domain {

  type WeatherEither[+T] = Either[Exception, T]
  type WeatherResult[+T] = Future[WeatherEither[T]]
}
