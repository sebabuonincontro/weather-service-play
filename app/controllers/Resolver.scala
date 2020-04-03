package controllers

import domain.{BoardNotFound, LocationNotFound, WeatherException}
import play.api.mvc.{BaseController, Result}
import play.api.mvc.Results._
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.{ExecutionContext, Future}

trait Resolver { _ : BaseController =>

  def resolve[T]( result: Either[Exception, T])(fnc: T => Result)(implicit ec: ExecutionContext) : Result = {
    result match {
      case Left(e: BoardNotFound) => NotFound(e.getMessage)
      case Left(e: LocationNotFound) => NotFound(e.getMessage)
      case Left(e: WeatherException) => InternalServerError(e.getMessage)
      case Left(e: Exception) => InternalServerError(e.getMessage)
      case Right(v: T) => fnc(v)
    }
  }
}

case class ErrorMessage(level: String = "ERROR", message: String)
