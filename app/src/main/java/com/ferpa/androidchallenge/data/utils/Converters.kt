package com.ferpa.androidchallenge.data.utils

import androidx.room.TypeConverter
import com.ferpa.androidchallenge.remote.dto.Coordinates
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromCoordinates(coordinates: Coordinates?): String? {
        return Gson().toJson(coordinates)
    }

    @TypeConverter
    fun toCoordinates(data: String?): Coordinates? {
        if (data == null) {
            return null
        }
        val type = object : TypeToken<Coordinates>() {}.type
        return Gson().fromJson(data, type)
    }
}