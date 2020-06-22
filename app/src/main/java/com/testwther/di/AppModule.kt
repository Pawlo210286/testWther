package com.testwther.di

import android.app.Application
import com.testwther.common.utils.PermissionChecker
import com.testwther.domain.resources.ResourceReader
import com.testwther.domain.useCase.WeatherInteractor
import com.testwther.domain.useCase.WeatherUseCase
import com.testwther.ui.common.AndroidResourceReader
import com.testwther.ui.data.CityTab
import com.testwther.ui.datasource.WeatherDataSource
import org.kodein.di.Kodein
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

object AppModule {

    fun get(application: Application) = Kodein.Module("App") {
        import(androidModule(application))
        import(StorageLocalModule.get())
        import(RemoteModule.get())

        bind<WeatherUseCase>() with provider {
            WeatherInteractor(
                gateway = instance()
            )
        }

        bind() from provider {
            WeatherDataSource(
                weatherUseCase = instance(),
                resources = instance()
            )
        }

        bind<WeatherDataSource>(tag = CityTab.PETER) with singleton {
            instance<WeatherDataSource>()
        }

        bind<WeatherDataSource>(tag = CityTab.MOSCOW) with singleton {
            instance<WeatherDataSource>()
        }

        bind<ResourceReader>() with singleton { AndroidResourceReader(application.applicationContext) }

        bind() from singleton {
            PermissionChecker(application)
        }
    }
}