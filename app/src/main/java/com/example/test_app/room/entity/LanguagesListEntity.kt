package com.example.test_app.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages_table")
data class LanguagesListEntity(

    @ColumnInfo(name = "iso639_1")
    val iso639_1: String,
    @ColumnInfo(name = "iso639_2")
    val iso639_2: String,
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "nativeName")
    val nativeName: String
)