package com.testwther.ui

import com.testwther.common.BaseFactory
import com.testwther.ui.datasource.WeatherDataSource

class MainFactory(
    private val peterDataSource: WeatherDataSource,
    private val moscowDataSource: WeatherDataSource
) : BaseFactory<MainViewModel>() {

    override fun createViewModel(): MainViewModel {
        return MainViewModel(
            peterDataSource = peterDataSource,
            moscowDataSource = moscowDataSource
        )
    }
}