package services

import domain.{DailyForecast, Forecast}
import javax.inject.Inject
import repositories.ForecastRepository

/**
 * manages Forecast data.
 * @param forecastRepository
 */
class ForecastService @Inject()(forecastRepository: ForecastRepository){

  def save(forecasts: List[DailyForecast], locationId: Long) = {
    val forecastList = forecasts.map{ daily =>
      Forecast(
        None,
        locationId,
        daily.dt,
        daily.temp.min,
        daily.temp.max,
        daily.humidity,
        daily.wind_speed,
        daily.weather.main + " - " + daily.weather.description,
        daily.clouds,
      daily.pop,
      Option(daily.rain),
      Option(daily.snow))
    }
    forecastRepository.save(forecastList)
  }
}
