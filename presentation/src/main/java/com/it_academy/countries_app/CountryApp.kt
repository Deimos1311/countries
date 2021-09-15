package com.it_academy.countries_app

import android.app.Application
import com.it_academy.countries_app.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CountryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            //Koin Android logger
            androidLogger()
            //Inject Android context
            androidContext(this@CountryApp)
            //Use modules
            modules(
                appModule,
                listOfCountriesModule,
                slidersModule,
                listOfCapitalsModule,
                myLocationModule,
                regionModule,
                newsModule
            )
        }
    }
}