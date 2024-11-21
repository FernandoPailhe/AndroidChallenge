package com.ferpa.androidchallenge.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ferpa.androidchallenge.data.local.CityDao
import com.ferpa.androidchallenge.domain.repository.CityRepository
import com.ferpa.androidchallenge.remote.ApiService
import com.ferpa.androidchallenge.remote.dto.City
import kotlinx.coroutines.flow.Flow

class CityRepositoryImpl(
    private val cityDao: CityDao,
    private val apiService: ApiService
) : CityRepository {

    override suspend fun fetchAndStoreCities() {
        try {
            val cityCount = cityDao.countCities()

            if (cityCount == 0) {
                val cities = apiService.getCities()

                cities.forEach { city ->
                    cityDao.insertCity(city)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getPagedCities(): Flow<PagingData<City>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { cityDao.getPagedCities() }
        ).flow
    }

    override fun searchCities(query: String): Flow<List<City>> {
        return cityDao.searchCities(query)
    }

}
