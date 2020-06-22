package com.testwther.ui.map

import com.testwther.common.BaseFactory
import com.testwther.common.utils.PermissionChecker
import com.testwther.domain.useCase.WeatherUseCase
import com.testwther.ui.map.common.MarkerAdapter

class MapFactory(
    private val permissionChecker: PermissionChecker,
    private val weatherUseCase: WeatherUseCase,
    private val markerAdapter: MarkerAdapter
) : BaseFactory<MapViewModel>() {

    override fun createViewModel(): MapViewModel {
        return MapViewModel(permissionChecker, weatherUseCase, markerAdapter)
    }
}