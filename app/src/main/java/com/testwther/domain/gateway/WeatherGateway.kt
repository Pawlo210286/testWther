package com.testwther.domain.gateway

import com.testwther.domain.data.dto.CityDTO
import com.testwther.domain.data.dto.WeatherDTO
import com.testwther.domain.data.entity.Weather
import com.testwther.domain.data.toCityLocalEntity
import com.testwther.domain.data.toWeather
import com.testwther.domain.data.toWeatherLocalEntity
import com.testwther.source.local.repository.city.ICityLocalRepository
import com.testwther.source.local.repository.weather.IWeatherLocalRepository
import com.testwther.source.remote.data.result.Result
import com.testwther.source.remote.repository.IWeatherRepository
import java.util.*

class WeatherGateway(
    private val remote: IWeatherRepository,
    private val localCities: ICityLocalRepository,
    private val localWeather: IWeatherLocalRepository
) : IWeatherGateway {

    override suspend fun getWeather(lat: Double, lon: Double): WeatherDTO? {
        return remote.getWeather(lat, lon).takeIf {
            it is Result.Success
        }?.let {
            it as Result.Success
            WeatherDTO(
                current = Weather(
                    icon = it.data.currently?.icon.orEmpty(),
                    temperature = it.data.currently?.temperature?.toFloat()?.toCelsius() ?: 0f,
                    date = Date(it.data.currently?.time?.toLong()?.toMillis() ?: 0L),
                    summary = it.data.currently?.summary.orEmpty()
                ),
                daily = it.data.daily?.data?.map {
                    Weather(
                        icon = it.icon.orEmpty(),
                        temperature = it.temperatureHigh?.toFloat()?.toCelsius() ?: 0f,
                        date = Date(it.time?.toLong()?.toMillis() ?: 0L),
                        summary = it.summary.orEmpty()
                    )
                }.orEmpty()
            )
        }
    }

    override suspend fun getWeatherByCity(city: CityDTO): WeatherDTO? {
        val cachedCity = localCities.getCityByName(city.cityName)
        return if (cachedCity == null) {
            val cityId = saveCity(city)
            getAndSaveWeather(cityId = cityId, city = city)
        } else {
            val calendar = Calendar.getInstance(Locale.ROOT)
            val localWeekWeather = localWeather.getWeekWeather(cachedCity.id, calendar.timeInMillis)
                .map { it.toWeather() }
            val localCurrentWeather =
                localWeather.getCurrentWeather(cachedCity.id, calendar.timeInMillis)?.toWeather()
            return if (localCurrentWeather == null || localWeekWeather.isEmpty()) {
                getAndSaveWeather(cityId = cachedCity.id, city = city)
            } else {
                WeatherDTO(current = localCurrentWeather, daily = localWeekWeather)
            }
        }
    }

    private fun Float.toCelsius(): Float {
        return (this - 32) * 5f / 9f
    }

    private fun Long.toMillis(): Long = this * 1000

    private suspend fun getAndSaveWeather(cityId: Long, city: CityDTO): WeatherDTO? {
        val remoteWeather = getWeather(city.lat, city.lon)
        remoteWeather?.let { saveWeatherToLocal(cityId, remoteWeather) }
        return remoteWeather
    }

    private suspend fun saveWeatherToLocal(cityId: Long, weather: WeatherDTO) {
        localWeather.clearWeatherByCityId(cityId)
        localWeather.saveWeather(
            listOf(
                weather.current.toWeatherLocalEntity(
                    cityId = cityId,
                    isCurrentWeather = true
                )
            )
        )
        localWeather.saveWeather(weather.daily.map {
            it.toWeatherLocalEntity(
                cityId = cityId,
                isCurrentWeather = false
            )
        })
    }

    private suspend fun saveCity(city: CityDTO): Long {
        return localCities.saveCity(city.toCityLocalEntity())
    }
}