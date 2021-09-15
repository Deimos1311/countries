package com.it_academy.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.it_academy.data.room.entity.CountryEntity
import com.it_academy.data.room.entity.CountryLanguageCrossRefEntity
import com.it_academy.data.room.entity.LanguageEntity

@Database(
    entities = [
        CountryEntity::class,
        LanguageEntity::class,
        CountryLanguageCrossRefEntity::class
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
