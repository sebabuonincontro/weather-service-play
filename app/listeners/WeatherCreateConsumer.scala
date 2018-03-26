package listeners

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import domain.ImplicitsJson._
import domain.Weather
import exceptions.WeatherServiceException
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import play.api.libs.json.Json
import play.api.{Configuration, Logger}
import repository.WeatherRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class WeatherCreateConsumer @Inject() (weatherRepository: WeatherRepository, configuration: Configuration) {

  val logger = Logger(this.getClass)
  val bootstrapServer = configuration.get[String]("kafka.bootstrapServer")
  val topic = "create-weather-topic"
  val groupId = "createWeather"

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()


  val consumerSettings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
    .withBootstrapServers(bootstrapServer)
    .withGroupId(groupId)
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  Consumer.committableSource(consumerSettings, Subscriptions.topics(topic))
    .mapAsync(1){ msg =>
      Json.parse(msg.record.value).validate[Weather].fold(
        error => {
          logger.error(s"Error while Consuming new weather $error")
          Future(throw WeatherServiceException(error.toString))
        },
        weather => {
          logger.info(s"Consumer Creating new weather ${weather}")
          weatherRepository.create(weather).map(_ => msg.committableOffset)
        }
      )
    }
    .mapAsync(3)(_.commitScaladsl())
    .runWith(Sink.ignore)
}