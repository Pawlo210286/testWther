package com.testwther.domain.data.dto

import com.testwther.domain.data.entity.Weather

data class WeatherDTO(
    val current: Weather,
    val daily: List<Weather>
)