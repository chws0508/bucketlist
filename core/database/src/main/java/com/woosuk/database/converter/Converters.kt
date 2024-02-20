package com.woosuk.database.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Converters {
    @TypeConverter
    fun localDateTimeToLong(localDateTime: LocalDateTime): Long =
        localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    @TypeConverter
    fun longToLocalDateTime(long: Long): LocalDateTime =
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(long),
            ZoneId.systemDefault(),
        )

    @TypeConverter
    fun stringListToString(value: List<String>): String {
        if (value.isNotEmpty()) {
            return Json.encodeToString(value)
        }
        return "[]"
    }

    @TypeConverter
    fun stringToListString(value: String): List<String> {
        return Json.decodeFromString(value)
    }
}
