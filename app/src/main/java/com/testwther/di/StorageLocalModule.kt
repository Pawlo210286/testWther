package com.testwther.di

import androidx.room.Room
import com.testwther.source.local.repository.LocalDatabase
import com.testwther.source.local.repository.city.CityDao
import com.testwther.source.local.repository.city.CityLocalRepository
import com.testwther.source.local.repository.city.ICityLocalRepository
import com.testwther.source.local.repository.weather.IWeatherLocalRepository
import com.testwther.source.local.repository.weather.WeatherDao
import com.testwther.source.local.repository.weather.WeatherLocalRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

object StorageLocalModule {
    fun get() = Kodein.Module("StorageLocalModule") {
        bind<LocalDatabase>() with singleton {
            Room.databaseBuilder<LocalDatabase>(
                instance(),
                LocalDatabase::class.java,
                LocalDatabase.NAME
            ).build()
        }

        applyDaoModule()
        applyRepositoryModule()
    }

    private fun Kodein.Builder.applyDaoModule() {
        bind<CityDao>() with provider { instance<LocalDatabase>().cityDao() }
        bind<WeatherDao>() with provider { instance<LocalDatabase>().weatherDao() }
    }

    private fun Kodein.Builder.applyRepositoryModule() {
        bind<ICityLocalRepository>() with provider { CityLocalRepository(instance()) }
        bind<IWeatherLocalRepository>() with provider { WeatherLocalRepository(instance()) }
    }
}