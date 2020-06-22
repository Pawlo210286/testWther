package com.testwther.ui.map

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ktx.addMarker
import com.testwther.common.utils.PermissionChecker
import com.testwther.domain.useCase.WeatherUseCase
import com.testwther.ui.map.common.MarkerAdapter
import kotlinx.coroutines.*

class MapViewModel(
    private val permissionChecker: PermissionChecker,
    private val weatherUseCase: WeatherUseCase,
    private val markerAdapter: MarkerAdapter
) : ViewModel(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private var map: GoogleMap? = null

    private var currentMarker: Marker? = null

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        map?.apply {
            uiSettings?.isZoomControlsEnabled = true
            uiSettings?.isZoomGesturesEnabled = true

            scope.launch {
                if (permissionChecker.checkLocationPermission()) {
                    withContext(Dispatchers.Main) {
                        isMyLocationEnabled = true
                    }
                }
            }

            setOnMapClickListener(this@MapViewModel)
            setInfoWindowAdapter(markerAdapter)
        }
    }

    override fun onCleared() {
        map = null
        job.cancel()
        super.onCleared()
    }

    override fun onMapClick(location: LatLng?) {
        scope.launch {
            location?.let {
                weatherUseCase.getWeather(it.latitude, it.longitude)
            }?.let {
                it.current
            }?.let {
                withContext(Dispatchers.Main) {
                    currentMarker?.remove()
                    currentMarker = map?.addMarker {
                        snippet(it.toString())
                        position(location)
                    }
                    currentMarker?.showInfoWindow()
                }
            }
        }
    }
}