package services

import domain.{Forecast, ForecastResponse}
import javax.inject.Inject
import repositories.ForecastRepository

class ForecastService @Inject()(forecastRepository: ForecastRepository){

  def save(forecasts: List[ForecastResponse], newsId: Long, woeid: String) = {
    val forecastList = forecasts.map{ item =>
      Forecast(None, newsId, woeid, item.day + " " + item.date, item.high.toInt, item.low.toInt, item.text)
    }
    forecastRepository.save(forecastList)
  }
}
