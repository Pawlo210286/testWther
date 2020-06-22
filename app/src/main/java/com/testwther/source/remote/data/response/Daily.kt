package com.testwther.source.remote.data.response


import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("data")
    val `data`: List<Data>?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("summary")
    val summary: String?
)