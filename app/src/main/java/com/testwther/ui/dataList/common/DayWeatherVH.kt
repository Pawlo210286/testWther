package com.testwther.ui.dataList.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testwther.R
import com.testwther.ui.data.WeatherUiEntity
import kotlinx.android.synthetic.main.item_day_weather.view.*

class DayWeatherVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: WeatherUiEntity) = with(itemView) {
        img_weather.setImageResource(item.iconResId)
        tv_temperature.text = item.temperature
        tv_date_text.text = item.date
    }

    companion object {
        fun newInstance(parent: ViewGroup): DayWeatherVH {
            return LayoutInflater.from(parent.context)
                .inflate(R.layout.item_day_weather, parent, false)
                .let { view -> DayWeatherVH(view) }
        }
    }
}