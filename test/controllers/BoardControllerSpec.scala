package controllers

import domain.{Board, BoardNotFound, BoardRequest, WeatherError, WeatherResult}
import io.circe.generic.auto._
import io.circe.syntax._
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfter, MustMatchers}
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import services.BoardService
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{ExecutionContext, Future}

class BoardControllerSpec
  extends PlaySpec
    with GuiceOneServerPerSuite
    with Injecting
    with MockitoSugar
    with MustMatchers
    with BeforeAndAfter {

  implicit val materializer = app.materializer

  var boardService: BoardService = _
  var controller: BoardController = _

  before {
    boardService = mock[BoardService]
    controller = new BoardController(stubControllerComponents(), boardService)
  }

  "BoardController" should {
    "create a board" when {
      "POST a new board return successful message" in {
        //given
        val expected = Board(Some(1), "description")
        val request = BoardRequest("description")

        //when
        when(boardService.save(request)).thenReturn(WeatherResult(expected))
        val board = controller.create()
          .apply(FakeRequest(POST, "/board")
            .withBody(request))

        //then
        status(board) mustBe CREATED
        contentType(board) mustBe Some("application/json")
        contentAsJson(board) mustBe Json.parse(expected.asJson.toString())
      }
      "Throw Error when it can't find a board" in {
        //given
        val request = BoardRequest("description")

        //when
        when(boardService.save(any[BoardRequest])).thenReturn(WeatherResult.error(BoardNotFound(1L)))
        val board = controller.create()
          .apply(FakeRequest(POST, "/board")
            .withBody(request))

        //then
        status(board) mustBe NOT_FOUND
        contentType(board) mustBe Some("application/json")
        //contentAsJson(board) mustBe Json.parse(expected.asJson.toString())
      }
    }
  }

}
