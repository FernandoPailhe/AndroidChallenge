package com.ferpa.androidchallenge.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ferpa.androidchallenge.remote.dto.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)

    @Query("SELECT COUNT(*) FROM city")
    fun countCities(): Int

    @Query(
        "SELECT * FROM city " +
                "WHERE (:onlyFavorites IS NULL OR isFavorite = :onlyFavorites) " +
                "ORDER BY name ASC, country ASC"
    )
    fun getPagedCities(onlyFavorites: Boolean? = null): PagingSource<Int, City>

    @Query(
        "SELECT * FROM city " +
                "WHERE (:onlyFavorites IS NULL OR isFavorite = :onlyFavorites) " +
                "AND (name LIKE :query || '%' OR country LIKE :query || '%') " +
                "UNION " +
                "SELECT * FROM city " +
                "WHERE (:onlyFavorites IS NULL OR isFavorite = :onlyFavorites) " +
                "AND (name LIKE '%' || :query || '%') " +
                "ORDER BY name ASC"
    )
    fun searchCitiesPaginated(query: String, onlyFavorites: Boolean? = null): PagingSource<Int, City>

    @Update
    suspend fun updateCity(city: City)

}