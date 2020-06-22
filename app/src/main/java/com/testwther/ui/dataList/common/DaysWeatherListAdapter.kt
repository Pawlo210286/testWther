package com.testwther.ui.dataList.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.testwther.ui.data.WeatherUiEntity

class DaysWeatherListAdapter : ListAdapter<WeatherUiEntity, DayWeatherVH>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayWeatherVH {
        return DayWeatherVH.newInstance(parent)
    }

    override fun onBindViewHolder(holder: DayWeatherVH, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<WeatherUiEntity>() {
            override fun areItemsTheSame(
                old: WeatherUiEntity,
                new: WeatherUiEntity
            ): Boolean {
                return old.date == new.date
            }

            override fun areContentsTheSame(
                old: WeatherUiEntity,
                new: WeatherUiEntity
            ): Boolean {
                return old == new
            }
        }
    }
}