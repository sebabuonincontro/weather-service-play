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
        daily.temp.min,
        daily.temp.max,
        daily.humidity,
        daily.wind_speed,
        "",
        daily.clouds,
      daily.pop,
      daily.rain,
      daily.snow)
    }
    forecastRepository.save(forecastList)
  }
}
