package com.testwther.ui.map.common

import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.testwther.R
import com.testwther.domain.data.entity.Weather
import com.testwther.domain.resources.ResourceReader
import com.testwther.ui.common.toUiEntity
import kotlinx.android.synthetic.main.view_marker.view.*

class MarkerAdapter(
    private val layoutInflater: LayoutInflater,
    private val resourceReader: ResourceReader
) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(marker: Marker?): View {
        return inflateMarker(marker)
    }

    override fun getInfoWindow(marker: Marker?): View {
        return inflateMarker(marker)
    }

    private fun inflateMarker(marker: Marker?): View {
        return layoutInflater.inflate(R.layout.view_marker, null, false).apply {
            marker?.snippet?.let {
                Weather.fromString(it)
            }?.let {
                it.toUiEntity(resourceReader)
            }?.let {
                img_weather.setImageResource(it.iconResId)
                tv_temperature.text = it.temperature
                tv_weather_desc.text = it.summary
            }
        }
    }
}