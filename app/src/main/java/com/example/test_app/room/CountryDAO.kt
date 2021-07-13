package com.example.test_app.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test_app.model.Country

@Dao
interface CountryDAO {

    @androidx.room.Query("SELECT * FROM countries_table")
    fun getAllCountries(): MutableList<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllCountries(mutableList: MutableList<EntityCountry>)

/*    @Query("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'Country_database'")
    fun clearPrimaryKey()*/
}
