package com.testwther.ui.common

import com.testwther.R
import com.testwther.domain.data.entity.Weather
import com.testwther.domain.resources.ResourceReader
import com.testwther.ui.data.WeatherUiEntity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun Weather.toUiEntity(resources: ResourceReader): WeatherUiEntity {
    val dateFormat = SimpleDateFormat("EE, dd MMMM", Locale.forLanguageTag("ru"))

    return WeatherUiEntity(
        iconResId = icon.toWeatherIcon(),
        temperature = resources.getString(R.string.degrees_text, temperature.roundToInt()),
        date = dateFormat.format(date),
        summary = summary
    )
}

fun String.toWeatherIcon(): Int {//todo: replace icon resources if needed
    return when (this) {
        "clear-day" -> R.drawable.ic_weather_template
        "clear-night" -> R.drawable.ic_weather_template
        "rain" -> R.drawable.ic_weather_template
        "snow" -> R.drawable.ic_weather_template
        "sleet" -> R.drawable.ic_weather_template
        "wind" -> R.drawable.ic_weather_template
        "fog" -> R.drawable.ic_weather_template
        "cloudy" -> R.drawable.ic_weather_template
        "partly-cloudy-day" -> R.drawable.ic_weather_template
        "partly-cloudy-night" -> R.drawable.ic_weather_template
        else -> R.drawable.ic_weather_template
    }
}