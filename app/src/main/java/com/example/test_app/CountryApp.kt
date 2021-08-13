package com.example.test_app

import android.app.Application
import com.example.test_app.room.CountryDAO
import com.example.test_app.room.CountryDatabase

class CountryApp : Application() {

    companion object {
        var countryDataBase: CountryDatabase? = null
        var daoCountry: CountryDAO? = null
    }

    override fun onCreate() {
        super.onCreate()
        countryDataBase = applicationContext?.let { CountryDatabase.getInstance(it) }
        daoCountry = countryDataBase?.countryDAO
    }
}