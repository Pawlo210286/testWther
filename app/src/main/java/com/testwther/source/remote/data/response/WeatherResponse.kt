package com.testwther.source.remote.data.response


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("currently")
    val currently: Currently?,
    @SerializedName("daily")
    val daily: Daily?,
    @SerializedName("flags")
    val flags: Flags?,
    @SerializedName("hourly")
    val hourly: Hourly?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("timezone")
    val timezone: String?,
    @SerializedName("summary")
    val summary: String?
)