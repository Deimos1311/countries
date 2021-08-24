package com.example.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.test_app.STRING_NULL_VALUE

@Entity(tableName = "languages_table")
data class LanguageEntity(

    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String = STRING_NULL_VALUE,
    @ColumnInfo(name = "nativeName")
    var nativeName: String = STRING_NULL_VALUE,
    @ColumnInfo(name = "iso639_1")
    var iso639_1: String = STRING_NULL_VALUE,
    @ColumnInfo(name = "iso639_2")
    var iso639_2: String = STRING_NULL_VALUE
)