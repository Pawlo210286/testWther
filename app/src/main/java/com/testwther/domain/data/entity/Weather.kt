package com.testwther.domain.data.entity

import java.util.*

data class Weather(
    val icon: String,
    val temperature: Float,
    val date: Date,
    val summary: String
) {
    override fun toString(): String {
        return "${icon},${temperature},${date.time},${summary}"
    }

    companion object {

        fun fromString(data: String): Weather {
            val list = data.split(",")
            return Weather(
                icon = list[0],
                temperature = list[1].toFloat(),
                date = Date(list[2].toLong()),
                summary = list[3]
            )
        }
    }
}