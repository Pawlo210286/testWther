package com.testwther.ui.dataList

import com.testwther.common.BaseFactory
import com.testwther.ui.data.CityTab
import com.testwther.ui.datasource.WeatherDataSource

class ViewModelFactory(
    private val dataSource: WeatherDataSource,
    private val cityTab: CityTab
) : BaseFactory<WeekWeatherViewModel>() {

    override fun createViewModel(): WeekWeatherViewModel {
        return WeekWeatherViewModel(dataSource = dataSource, cityTab = cityTab)
    }
}