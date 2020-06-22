package com.testwther.source.local.repository.city

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testwther.source.local.entity.CityLocalEntity

@Dao
interface CityDao {
    @Query("DELETE FROM CityLocalEntity")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: CityLocalEntity): Long

    @Query("SELECT * FROM CityLocalEntity WHERE cityName = :cityName")
    suspend fun getCityByName(cityName: String): CityLocalEntity?
}