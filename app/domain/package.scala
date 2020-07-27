import scala.concurrent.Future

package object domain {

  type WeatherEither[+T] = Either[WeatherError, T]
  type WeatherResult[+T] = Future[WeatherEither[T]]

  object WeatherResult {
    def apply[T](t: T): WeatherResult[T] = Future.successful(Right(t))
    def error(error: WeatherError) = Future.successful(Left(error))
  }
}
