package com.testwther.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.testwther.BuildConfig
import com.testwther.domain.gateway.IWeatherGateway
import com.testwther.domain.gateway.WeatherGateway
import com.testwther.source.remote.api.WeatherApi
import com.testwther.source.remote.repository.IWeatherRepository
import com.testwther.source.remote.repository.WeatherRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RemoteModule {
    fun get() = Kodein.Module("Remote") {
        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(instance())
                .build()
        }

        bind<OkHttpClient>() with singleton {
            val builder = OkHttpClient.Builder()

            builder.cache(instance())

            builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            builder.retryOnConnectionFailure(true)

            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
            }

            builder.build()
        }

        bind() from provider {
            val cacheSize = 10 * 1024 * 1024 // 10 MB
            Cache(instance<Context>().cacheDir, cacheSize.toLong())
        }

        bind() from singleton {
            instance<Retrofit>().create(WeatherApi::class.java)
        }

        bind<IWeatherRepository>() with provider {
            WeatherRepository(
                api = instance()
            )
        }

        bind<IWeatherGateway>() with provider {
            WeatherGateway(
                remote = instance(),
                localCities = instance(),
                localWeather = instance()
            )
        }
    }

    private const val TIMEOUT: Long = 100
}