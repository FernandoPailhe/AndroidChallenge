package com.ferpa.androidchallenge.domain.repository

import androidx.paging.PagingData
import com.ferpa.androidchallenge.remote.dto.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    suspend fun fetchAndStoreCities(onProgress: (Int) -> Unit)
    fun getPagedCities(onlyFavorites: Boolean? = null): Flow<PagingData<City>>
    fun searchCities(query: String, onlyFavorites: Boolean? = null): Flow<List<City>>
    suspend fun updateCity(updatedCity: City)
}