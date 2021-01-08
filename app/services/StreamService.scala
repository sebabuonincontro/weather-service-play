package services

import java.nio.file.Paths

import akka.Done
import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.util.ByteString
import domain.Implicits._
import domain.{BoardRequest, StreamError}
import io.circe.parser.decode
import javax.inject.Inject
import play.api.Logging

import scala.concurrent.{ExecutionContext, Future}

class StreamService @Inject()(boardService: BoardService)
                             (implicit val system: ActorSystem, ec: ExecutionContext) extends Logging {

  val path = "conf/boards.txt"

  def run(): Future[Either[StreamError, Done]] = {


    val source = FileIO.fromPath(Paths.get(path))
      .via(Framing.delimiter(ByteString("\n"), 256, true).map(_.utf8String))
      .map(line =>
        decode[BoardRequest](line)
      ).map(_.fold(
        error => {
          logger.error(s"Error procesing Stream: ${error.getMessage}", error)
          Left(StreamError(error.getMessage))
        },
        board => {
          logger.info(s"Processing Stream board: $board")
          boardService.save(board)
        }
      ))

    source
      .toMat(Sink.ignore)(Keep.right)
      .run()
      .map(Right(_))
  }

}
