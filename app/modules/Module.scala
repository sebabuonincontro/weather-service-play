package modules

import actors.NewsActor
import com.google.inject.{AbstractModule, Provides}
import domain.OpenWeatherConfiguration
import javax.inject.Singleton
import play.api.libs.concurrent.AkkaGuiceSupport
import play.api.{Configuration, Environment}

/**
 * Main Guice module
 * Add Configuration & Environment to Module Parameters when necessary.
 *
 * @param environment   Play Environment
 * @param configuration Play Configuration instance
 */
class Module (environment: Environment, configuration: Configuration) extends AbstractModule with AkkaGuiceSupport {


  /**
   * Bind a [[akka.actor.typed.ActorSystem]] instance for dependency injection.
   *
   * @param newsActor A [[actors.NewsActor]] instance.
   * @return A [[akka.actor.typed.ActorSystem]] instance.
   */
  @Provides @Singleton
  def newsActorSystem(newsActor: NewsActor): akka.actor.typed.ActorSystem[actors.NewsActor.WeatherMessage] =
    akka.actor.typed.ActorSystem[actors.NewsActor.WeatherMessage](newsActor.behavior, NewsActor.name)

  /**
   * Bind a [[akka.actor.typed.ActorRef]] instance for dependency injection.
   *
   * @param system A [[akka.actor.typed.ActorSystem]] instance.
   * @return A [[akka.actor.typed.ActorRef]] instance.
   */
  @Provides @Singleton
  def newsActorRef(system: akka.actor.typed.ActorSystem[actors.NewsActor.WeatherMessage]) = system.ref

  /**
   * Bind a [[domain.OpenWeatherConfiguration]] instance for dependency injection.
   *
   * @return A [[domain.OpenWeatherConfiguration]] instance.
   */
  @Provides
  def yahooConfiguration: OpenWeatherConfiguration =
    OpenWeatherConfiguration(
      apiKey = configuration.get[String]("open-weather.api-key"),
      urlId = configuration.get[String]("open-weather.url-id"),
      urlForecast = configuration.get[String]("open-weather.url-forecast"),
      limit = configuration.get[Int]("open-weather.petition.limit")
    )

}
