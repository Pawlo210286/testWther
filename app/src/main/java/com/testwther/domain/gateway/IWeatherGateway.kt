package com.testwther.domain.gateway

import com.testwther.domain.data.dto.CityDTO
import com.testwther.domain.data.dto.WeatherDTO

interface IWeatherGateway {

    suspend fun getWeather(lat: Double, lon: Double): WeatherDTO?

    suspend fun getWeatherByCity(city: CityDTO): WeatherDTO?
}