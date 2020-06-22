package com.testwther.source.local.repository.city

import com.testwther.source.local.entity.CityLocalEntity
import com.testwther.source.local.repository.LocalDatabase

class CityLocalRepository(
    private val database: LocalDatabase
) : ICityLocalRepository {

    override suspend fun saveCity(city: CityLocalEntity): Long = database.cityDao().insert(city)

    override suspend fun clearCities() = database.cityDao().clear()

    override suspend fun getCityByName(cityName: String): CityLocalEntity? =
        database.cityDao().getCityByName(cityName)
}