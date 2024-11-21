package com.ferpa.androidchallenge.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ferpa.androidchallenge.remote.dto.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)

    @Query("SELECT COUNT(*) FROM city")
    fun countCities(): Int

    @Query("SELECT * FROM city ORDER BY name ASC, country ASC")
    fun getPagedCities(): PagingSource<Int, City>

    @Query(
        "SELECT * FROM city " +
                "WHERE name LIKE :query || '%' " +
                "OR country LIKE :query || '%' " +
                "ORDER BY name ASC"
    )
    fun searchCities(query: String): Flow<List<City>>

}