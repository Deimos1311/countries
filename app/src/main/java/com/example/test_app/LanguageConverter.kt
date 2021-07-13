/*package com.example.test_app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.example.test_app.model.LanguagesList

class LanguageConverter {
    companion object {

        @RequiresApi(Build.VERSION_CODES.N)
        @TypeConverter
        fun toString(languages: MutableList<LanguagesList>): String {
            return listOf(languages).joinToString()
        }

        @TypeConverter
        fun toList(languages: String): MutableList<LanguagesList> {
            return languages
        }
    }
}*/
