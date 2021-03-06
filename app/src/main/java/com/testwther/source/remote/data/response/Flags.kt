package com.testwther.source.remote.data.response


import com.google.gson.annotations.SerializedName

data class Flags(
    @SerializedName("meteoalarm-license")
    val meteoalarmLicense: String?,
    @SerializedName("nearest-station")
    val nearestStation: Double?,
    @SerializedName("sources")
    val sources: List<String>?,
    @SerializedName("units")
    val units: String?
)