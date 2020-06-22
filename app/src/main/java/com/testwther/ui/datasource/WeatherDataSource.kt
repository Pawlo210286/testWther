package com.testwther.ui.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.testwther.domain.data.dto.CityDTO
import com.testwther.domain.resources.ResourceReader
import com.testwther.domain.useCase.WeatherUseCase
import com.testwther.ui.common.toUiEntity
import com.testwther.ui.data.CityTab
import com.testwther.ui.data.WeatherUiEntity

class WeatherDataSource(
    private val weatherUseCase: WeatherUseCase,
    private val resources: ResourceReader
) {

    private var _current: WeatherUiEntity? = null
        set(value) {
            field = value
            currentSource.postValue(value)
        }

    private val currentSource = MutableLiveData<WeatherUiEntity>()
    val current: LiveData<WeatherUiEntity>
        get() = currentSource

    private var _daily: List<WeatherUiEntity> = emptyList()
        set(value) {
            field = value
            dailySource.postValue(value)
        }

    private val dailySource = MutableLiveData<List<WeatherUiEntity>>()
    val daily: LiveData<List<WeatherUiEntity>>
        get() = dailySource

    suspend fun invalidate(cityTab: CityTab) {
        weatherUseCase.getWeatherByCity(
            CityDTO(
                cityName = cityTab.name,
                lat = cityTab.location.lat,
                lon = cityTab.location.lon
            )
        ).let {
            _current = it.current.toUiEntity(resources)
            _daily = it.daily.map { it.toUiEntity(resources) }
        }
    }

    suspend fun invalidate(lat: Double, lon: Double) {
        weatherUseCase.getWeather(lat, lon).let {
            _current = it.current.toUiEntity(resources)
            _daily = it.daily.map { it.toUiEntity(resources) }
        }
    }
}