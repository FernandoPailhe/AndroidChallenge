package com.ferpa.androidchallenge.domain.repository

import androidx.paging.PagingData
import com.ferpa.androidchallenge.remote.dto.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    suspend fun fetchAndStoreCities()
    fun getPagedCities(): Flow<PagingData<City>>
    fun searchCities(query: String): Flow<List<City>>
}