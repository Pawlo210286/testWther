package com.testwther.source.local.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.testwther.source.local.entity.CityLocalEntity
import com.testwther.source.local.entity.WeatherLocalEntity
import com.testwther.source.local.repository.city.CityDao
import com.testwther.source.local.repository.weather.WeatherDao

@Database(
    entities = [CityLocalEntity::class, WeatherLocalEntity::class],
    version = 1
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        const val NAME = "WeatherDatabase"
    }
}