package com.testwther.ui.data

import androidx.annotation.DrawableRes

data class WeatherUiEntity(
    @DrawableRes val iconResId: Int,
    val temperature: String,
    val date: String,
    val summary: String
)