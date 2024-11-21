package com.ferpa.androidchallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ferpa.androidchallenge.data.utils.Converters
import com.ferpa.androidchallenge.remote.dto.City

@Database(
    version = 2,
    entities = [
        City::class,
    ],
    exportSchema = true
)

@TypeConverters(Converters::class)
abstract class CityRoomDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "cities_db"
    }

    abstract fun cityDao(): CityDao
}