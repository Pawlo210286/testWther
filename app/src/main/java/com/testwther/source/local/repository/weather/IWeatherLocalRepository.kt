package com.testwther.source.local.repository.weather

import com.testwther.source.local.entity.WeatherLocalEntity

interface IWeatherLocalRepository {
    suspend fun getCurrentWeather(cityId: Long, currentDate: Long): WeatherLocalEntity?
    suspend fun getWeekWeather(cityId: Long, currentDate: Long): List<WeatherLocalEntity>
    suspend fun saveWeather(items: List<WeatherLocalEntity>)
    suspend fun clearWeatherByCityId(cityId: Long)
}