package com.testwther.source.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(
            value = ["cityName"], unique = true
        )
    ]
)
data class CityLocalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cityName: String,
    val lon: Double,
    val lat: Double
)