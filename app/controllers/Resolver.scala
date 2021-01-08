package controllers

import domain.{BoardNotFound, DataBaseError, ForecastNotFound, LocationNotFound, StreamError, WeatherDomain, WeatherError, YahooRequestLimitExceeded, YahooServiceError}
import play.api.Logging
import play.api.mvc.{BaseController, Result}
import io.circe.syntax._

import scala.concurrent.ExecutionContext
import domain.Implicits._

/**
 * Trait contains a resolver and an handler error for Controller purpose.
 */
trait Resolver { _ : BaseController with Logging =>

  /**
   *
   * @param result result of the operation
   * @param fnc fnc to apply in case of success
   * @param ec ExecutionContext
   * @return [[Result]]
   */
  def resolve[T]( result: Either[WeatherError, T])(fnc: T => Result)(implicit ec: ExecutionContext): Result = {
    result match {
      case Right(v: T) => fnc(v)
      case Left(e: BoardNotFound) => NotFound(e.message)
      case Left(e: LocationNotFound) => NotFound(e.message)
      case Left(e: WeatherError) =>
        logger.error(s"Unexpected Error: ${e.message}")
        InternalServerError(e.message)
      case Left(e: StreamError) =>
        logger.error(s"Stream Error: ${e.message}")
        InternalServerError(e.message)
    }
  }

  /**
   * Converts a specific Intentions application error to the best matching Play framework Result for it.
   *
   * @param error Specific Intentions application to convert.
   * @return      Proper Play framework Result that should be returned to the requesting HTTP client according to the
   *              and content of the given error.
   */
//  def handleApplicationError(error: WeatherError): Result = error match {
//    case _: BoardNotFound               => NotFound(error.asJson)
//    case _: LocationNotFound            => NotFound(error.asJson)
//    case _: ForecastNotFound            => NotFound(error.asJson)
//    case _: YahooRequestLimitExceeded   => InternalServerError(error.asJson)
//    case _: YahooServiceError           => InternalServerError(error.asJson)
//    case _: DataBaseError               => InternalServerError(error.asJson)
//  }
}
