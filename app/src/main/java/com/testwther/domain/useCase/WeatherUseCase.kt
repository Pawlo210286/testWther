package com.testwther.domain.useCase

import com.testwther.domain.data.dto.CityDTO
import com.testwther.domain.data.dto.WeatherDTO

interface WeatherUseCase {
    suspend fun getWeather(lat: Double, lon: Double): WeatherDTO
    suspend fun getWeatherByCity(cityDTO: CityDTO): WeatherDTO
}