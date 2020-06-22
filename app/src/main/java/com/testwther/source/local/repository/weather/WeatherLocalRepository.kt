package com.testwther.source.local.repository.weather

import com.testwther.source.local.entity.WeatherLocalEntity
import com.testwther.source.local.repository.LocalDatabase

class WeatherLocalRepository(
    private val database: LocalDatabase
) : IWeatherLocalRepository {
    override suspend fun getCurrentWeather(cityId: Long, currentDate: Long): WeatherLocalEntity? {
        return database.weatherDao()
            .getCurrentWeather(
                cityId,
                startDate = currentDate - CACHE_LIVE_PERIOD,
                currentDate = currentDate
            )
    }

    override suspend fun getWeekWeather(cityId: Long, currentDate: Long): List<WeatherLocalEntity> {
        return database.weatherDao()
            .getWeekWeather(
                cityId,
                startDate = currentDate - CACHE_LIVE_PERIOD,
                currentDate = currentDate
            )
    }

    override suspend fun saveWeather(items: List<WeatherLocalEntity>) =
        database.weatherDao().insert(items = items)

    override suspend fun clearWeatherByCityId(cityId: Long) =
        database.weatherDao().deleteWeatherByCityId(cityId)

    companion object {
        private const val CACHE_LIVE_PERIOD = 1 * 60 * 60 * 1000 // 1 hour in millis
    }
}