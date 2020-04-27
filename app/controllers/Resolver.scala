package controllers

import domain.{BoardNotFound, LocationNotFound, WeatherException}
import play.api.Logging
import play.api.mvc.{BaseController, Result}

import scala.concurrent.ExecutionContext

trait Resolver { _ : BaseController with Logging =>

  def resolve[T]( result: Either[Exception, T])(fnc: T => Result)(implicit ec: ExecutionContext) : Result = {
    result match {
      case Right(v: T) => fnc(v)
      case Left(e: BoardNotFound) => NotFound(e.getMessage)
      case Left(e: LocationNotFound) => NotFound(e.getMessage)
      case Left(e: WeatherException) =>
        logger.error("Unhandled Error:", e)
        InternalServerError(e.getMessage)
      case Left(e: Exception) =>
        logger.error("Unexpected Error:", e)
        InternalServerError(e.getMessage)

    }
  }
}

case class ErrorMessage(level: String = "ERROR", message: String)
