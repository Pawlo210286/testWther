package com.testwther.source.remote.api

import com.testwther.source.remote.data.response.WeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {

    @GET("{location}?lang=ru")
    fun getWeatherAsync(
        @Path("location")
        location: String
    ): Deferred<Response<WeatherResponse>>
}