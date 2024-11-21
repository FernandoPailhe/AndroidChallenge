package com.ferpa.androidchallenge.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "city")
data class City(
    @PrimaryKey
    @SerializedName("_id")
    val id: Int,
    val name: String,
    val country: String,
    val coord: Coordinates,
    val isFavorite: Boolean = false
)

data class Coordinates(
    val lon: Double,
    val lat: Double
)