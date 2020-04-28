package controllers

import akka.stream.Materializer
import domain.{Board, BoardRequest, WeatherException}
import io.circe.generic.auto._
import io.circe.syntax._
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach, MustMatchers}
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import services.BoardService

import scala.concurrent.{ExecutionContext, Future}

class BoardControllerSpec
  extends PlaySpec
    with GuiceOneServerPerSuite
    with Injecting
    with MockitoSugar
    with MustMatchers
    with BeforeAndAfter {

  implicit val materializer = app.materializer
  implicit val ec = mock[ExecutionContext]
  val boardService = mock[BoardService]
  val controller = new BoardController(stubControllerComponents(), boardService)

  "BoardController" should {
    "create a board" when {
      "POST a new board return successful message" in {
        //given
        val expected = Board(Some(1), "description")
        val request = BoardRequest("description")

        //when
        when(boardService.save(any[BoardRequest])).thenReturn(Future(Right(expected)))
        val board = controller.create()
          .apply(FakeRequest(POST, "/board")
            .withBody(request))

        //then
        status(board) mustBe CREATED
        contentType(board) mustBe Some("application/json")
        contentAsJson(board) mustBe Json.parse(expected.asJson.toString())
      }
      "Throw Error when there is not Board" in {
        //given
        val request = BoardRequest("description")

        //when
        when(boardService.save(any[BoardRequest])).thenReturn(Future(Left(new WeatherException("error"))))
        val board = controller.create()
          .apply(FakeRequest(POST, "/board")
            .withBody(request))

        //then
        status(board) mustBe INTERNAL_SERVER_ERROR
        contentType(board) mustBe Some("application/json")
        //contentAsJson(board) mustBe Json.parse(expected.asJson.toString())
      }
    }
  }

}
