package com.example.test_app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity

@Database(
    entities = [
        CountryEntity::class,
        LanguagesListEntity::class,
        CountryLanguageCrossRef::class
    ],
    version = 1
)
abstract class CountryDatabase : RoomDatabase() {

    abstract val countryDAO: CountryDAO

    companion object {

        @Volatile
        private var INSTANCE: CountryDatabase? = null

        fun getInstance(context: Context): CountryDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CountryDatabase::class.java,
                    "Country_database"
                ).allowMainThreadQueries()
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}
