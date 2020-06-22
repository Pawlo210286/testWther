package com.testwther.source.local.repository.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testwther.source.local.entity.WeatherLocalEntity

@Dao
interface WeatherDao {
    @Query("DELETE FROM WeatherLocalEntity")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<WeatherLocalEntity>)

    @Query(
        "SELECT * FROM WeatherLocalEntity " +
                "WHERE cityID = :cityId AND timestamp BETWEEN :startDate AND :currentDate AND isCurrentWeather = 0"
    )
    suspend fun getWeekWeather(
        cityId: Long,
        startDate: Long,
        currentDate: Long
    ): List<WeatherLocalEntity>

    @Query(
        "SELECT * FROM WeatherLocalEntity " +
                "WHERE cityID = :cityId AND timestamp BETWEEN :startDate AND :currentDate AND isCurrentWeather = 1 limit 1"
    )
    suspend fun getCurrentWeather(
        cityId: Long,
        startDate: Long,
        currentDate: Long
    ): WeatherLocalEntity

    @Query("DELETE FROM WeatherLocalEntity WHERE cityId = :cityId")
    suspend fun deleteWeatherByCityId(cityId: Long)
}