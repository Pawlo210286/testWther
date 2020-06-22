package com.testwther.source.remote.repository

import com.testwther.source.remote.api.WeatherApi
import com.testwther.source.remote.data.response.WeatherResponse
import com.testwther.source.remote.data.result.Result
import kotlinx.coroutines.Deferred
import retrofit2.Response

class WeatherRepository(
    private val api: WeatherApi
) : IWeatherRepository {

    private suspend fun <T : Any> execute(block: suspend () -> Deferred<Response<T>>): Result<T> {
        return try {
            block().await().let {
                calculateResult(it)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun <T> calculateResult(data: Response<T>): Result<T> {
        return data.code().takeIf {
            it !in CODE_SUCCESS_START..CODE_SUCCESS_END
        }?.let {
            getErrorResult(it, data)
        } ?: run {
            data.body()?.let {
                Result.Success(it)
            } ?: run {
                Result.Error<T>(NullPointerException("Response is empty"))
            }
        }
    }

    private fun <T> getErrorResult(
        code: Int,
        data: Response<T>
    ): Result.Error<T> {
        return Result.Error(IllegalArgumentException("${code}: ${data.errorBody()?.string()}"))
    }

    override suspend fun getWeather(lat: Double, lon: Double): Result<WeatherResponse> {
        return execute {
            api.getWeatherAsync("$lat,$lon")
        }
    }

    companion object {
        private const val CODE_SUCCESS_START = 200
        private const val CODE_SUCCESS_END = 299
    }
}