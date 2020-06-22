package com.testwther.source.remote.repository

import com.testwther.source.remote.data.response.WeatherResponse
import com.testwther.source.remote.data.result.Result

interface IWeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Result<WeatherResponse>
}