package com.example.test_app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EntityCountry::class], version = 1)
abstract class CountryDatabase: RoomDatabase() {
    abstract fun countryDAO(): CountryDAO

    companion object {
        fun init(context: Context) =
            Room.databaseBuilder(context, CountryDatabase::class.java, "Country_database")
                .allowMainThreadQueries()
                .build()
    }
}
