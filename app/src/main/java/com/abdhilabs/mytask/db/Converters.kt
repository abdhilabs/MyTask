package com.abdhilabs.mytask.db

import androidx.room.TypeConverter
import com.abdhilabs.mytask.data.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}