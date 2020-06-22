package com.testwther.ui.dataList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.testwther.ui.data.CityTab
import com.testwther.ui.data.WeatherUiEntity
import com.testwther.ui.datasource.WeatherDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeekWeatherViewModel(
    dataSource: WeatherDataSource,
    cityTab: CityTab
) : ViewModel() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    val dailyWeather: LiveData<List<WeatherUiEntity>> = dataSource.daily

    init {
        scope.launch {
            dataSource.invalidate(cityTab)
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}