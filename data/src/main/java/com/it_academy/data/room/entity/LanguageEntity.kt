package com.it_academy.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.it_academy.domain.STRING_NOT_AVAILABLE

@Entity(tableName = "languages_table")
data class LanguageEntity(

    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String = STRING_NOT_AVAILABLE,
    @ColumnInfo(name = "nativeName")
    var nativeName: String = STRING_NOT_AVAILABLE,
    @ColumnInfo(name = "iso639_1")
    var iso639_1: String = STRING_NOT_AVAILABLE,
    @ColumnInfo(name = "iso639_2")
    var iso639_2: String = STRING_NOT_AVAILABLE
)