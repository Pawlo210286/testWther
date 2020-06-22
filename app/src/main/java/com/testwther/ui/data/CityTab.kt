package com.testwther.ui.data

import com.testwther.R

enum class CityTab(
    val title: Int,
    val location: Location
) {
    PETER(
        title = R.string.tab_peter_text,
        location = Location(
            lat = 59.950015,
            lon = 30.316599
        )
    ),
    MOSCOW(
        title = R.string.tab_moscow_text,
        location = Location(
            lat = 55.753913,
            lon = 37.620836
        )
    )
}