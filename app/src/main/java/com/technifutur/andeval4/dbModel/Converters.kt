package com.technifutur.andeval4.dbModel

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.Date
@ProvidedTypeConverter
class Converters(date: Date) {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) } }
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}