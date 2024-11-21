package com.ferpa.androidchallenge.domain.repository

import androidx.paging.PagingData
import com.ferpa.androidchallenge.remote.dto.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    suspend fun fetchAndStoreCities(onProgress: (Int) -> Unit)
    /**
     * Retrieves paginated cities from the database.
     *
     * @param onlyFavorites Optional filter to include only favorite cities.
     *                       If `true`, only favorite cities are included.
     *                       If `false`, only non-favorite cities are included.
     *                       If `null`, all cities are included.
     * @return A [Flow] emitting [PagingData] for cities based on the filter criteria.
     */
    fun getPagedCities(onlyFavorites: Boolean? = null): Flow<PagingData<City>>

    /**
     * Searches for cities in the database with pagination support.
     *
     * The search is performed by matching the query with city names or country codes.
     * Results are ordered alphabetically by city name and then by country.
     *
     * @param query The search query used to filter cities. Matches are case-insensitive.
     *              Cities are included if their name contains the query as a substring
     *              or if their country code starts with the query.
     * @param onlyFavorites Optional filter to include only favorite cities.
     *                       If `true`, only favorite cities are included.
     *                       If `false`, only non-favorite cities are included.
     *                       If `null`, all cities are included.
     * @return A [Flow] emitting [PagingData] for cities based on the search and filter criteria.
     */
    fun searchCitiesPaginated(query: String, onlyFavorites: Boolean? = null): Flow<PagingData<City>>

    suspend fun updateCity(updatedCity: City)
}