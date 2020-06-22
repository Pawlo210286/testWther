package com.testwther.source.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.NO_ACTION
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CityLocalEntity::class,
            parentColumns = ["id"],
            childColumns = ["cityId"],
            onUpdate = CASCADE,
            onDelete = NO_ACTION
        )
    ],
    indices = [
        Index(
            value = ["cityId"]
        )
    ]
)
data class WeatherLocalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cityId: Long,
    val weatherDate: Long,
    val temperature: Float,
    val icon: String?,
    val summary: String?,
    val isCurrentWeather: Boolean,
    val timestamp: Long = Calendar.getInstance().timeInMillis
)