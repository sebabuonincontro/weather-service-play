package modules

import actors.WeatherActor
import com.google.inject.AbstractModule
import listeners.WeatherCreateConsumer
import play.api.libs.concurrent.AkkaGuiceSupport

class Module extends AbstractModule with AkkaGuiceSupport {

  override def configure() = {
    bind(classOf[WeatherCreateConsumer]).asEagerSingleton()
    bindActor[WeatherActor]("weather-actor")
  }
}
