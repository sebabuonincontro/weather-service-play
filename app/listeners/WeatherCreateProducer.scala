package listeners

import javax.inject.Inject

import cakesolutions.kafka.KafkaProducer.Conf
import cakesolutions.kafka.{KafkaProducer, KafkaProducerRecord}
import com.google.inject.Singleton
import domain.Weather
import org.apache.kafka.common.serialization.StringSerializer
import play.api.libs.json.Json
import play.api.{Configuration, Logger}
import domain.ImplicitsJson._

@Singleton
class WeatherCreateProducer @Inject()(configuration: Configuration) {

  val bootstrapServer = configuration.get[String]("kafka.bootstrapServer")
  val logger = Logger(this.getClass)
  val topic = "create-weather-topic"

  val producer = KafkaProducer(
    Conf(new StringSerializer(), new StringSerializer(), bootstrapServers = bootstrapServer)
  )

  def create(weather: Weather): Unit = {
    weather.id match {
      case Some(id) => {
        logger.info(s"Producer Creating weather: $weather")
        val record = KafkaProducerRecord(topic, id.toString, Json.toJson(weather).toString)
        producer.send(record)
      }
    }
  }
}
