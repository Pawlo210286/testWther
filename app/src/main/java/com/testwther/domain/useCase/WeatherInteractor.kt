package com.testwther.domain.useCase

import com.testwther.domain.data.dto.CityDTO
import com.testwther.domain.data.dto.WeatherDTO
import com.testwther.domain.gateway.IWeatherGateway

class WeatherInteractor(
    private val gateway: IWeatherGateway
) : WeatherUseCase {

    override suspend fun getWeather(lat: Double, lon: Double): WeatherDTO {
        return gateway.getWeather(lat, lon) ?: throw IllegalStateException("something went wrong")
    }

    override suspend fun getWeatherByCity(cityDTO: CityDTO): WeatherDTO {
        return gateway.getWeatherByCity(cityDTO)
            ?: throw IllegalStateException("something went wrong")
    }
}