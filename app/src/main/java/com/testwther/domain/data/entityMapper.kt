package com.testwther.domain.data

import com.testwther.domain.data.dto.CityDTO
import com.testwther.domain.data.entity.Weather
import com.testwther.source.local.entity.CityLocalEntity
import com.testwther.source.local.entity.WeatherLocalEntity
import java.util.*

fun CityDTO.toCityLocalEntity(): CityLocalEntity {
    return CityLocalEntity(
        cityName = cityName,
        lon = lon,
        lat = lat
    )
}

fun CityLocalEntity.toCityDTO(): CityDTO {
    return CityDTO(
        cityName = cityName,
        lat = lat,
        lon = lon
    )
}

fun WeatherLocalEntity.toWeather(): Weather {
    val calendar = Calendar.getInstance(Locale.ROOT)
    calendar.timeInMillis = weatherDate
    val weatherDate = calendar.time
    return Weather(
        icon = icon.orEmpty(),
        temperature = temperature,
        date = weatherDate,
        summary = summary.orEmpty()
    )
}

fun Weather.toWeatherLocalEntity(cityId: Long, isCurrentWeather: Boolean): WeatherLocalEntity {
    return WeatherLocalEntity(
        cityId = cityId,
        weatherDate = date.time,
        temperature = temperature,
        icon = icon,
        summary = summary,
        isCurrentWeather = isCurrentWeather
    )
}
