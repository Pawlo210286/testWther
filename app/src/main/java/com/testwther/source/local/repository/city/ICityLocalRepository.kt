package com.testwther.source.local.repository.city

import com.testwther.source.local.entity.CityLocalEntity

interface ICityLocalRepository {
    suspend fun saveCity(city: CityLocalEntity): Long
    suspend fun clearCities()
    suspend fun getCityByName(cityName: String): CityLocalEntity?
}