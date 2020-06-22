package com.testwther.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.testwther.ui.data.CityTab
import com.testwther.ui.data.WeatherUiEntity
import com.testwther.ui.datasource.WeatherDataSource

class MainViewModel(
    private val peterDataSource: WeatherDataSource,
    private val moscowDataSource: WeatherDataSource
) : ViewModel() {

    private var _currentCityTab: CityTab = CityTab.PETER
        set(value) {
            field = value
            currentTab.postValue(value)
        }

    private val currentTab = MutableLiveData<CityTab>().apply {
        value = CityTab.PETER
    }

    val currentWeather: LiveData<WeatherUiEntity> = Transformations.switchMap(currentTab) {
        when (it) {
            CityTab.PETER -> peterDataSource.current
            CityTab.MOSCOW -> moscowDataSource.current
            else -> peterDataSource.current
        }
    }

    fun onPageSelected(position: Int) {
        _currentCityTab = CityTab.values()[position]
    }
}
