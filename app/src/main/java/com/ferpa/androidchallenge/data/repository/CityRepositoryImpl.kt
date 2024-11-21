package com.ferpa.androidchallenge.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ferpa.androidchallenge.data.local.CityDao
import com.ferpa.androidchallenge.domain.repository.CityRepository
import com.ferpa.androidchallenge.remote.ApiService
import com.ferpa.androidchallenge.remote.dto.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CityRepositoryImpl(
    private val cityDao: CityDao,
    private val apiService: ApiService
) : CityRepository {

    override suspend fun fetchAndStoreCities(onProgress: (Int) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val cityCount = cityDao.countCities()

                if (cityCount == 0) {
                    val cities = apiService.getCities()
                    val totalCities = cities.size

                    cities.forEachIndexed { index, city ->
                        cityDao.insertCity(city)
                        val progress = ((index + 1) * 100) / totalCities
                        onProgress(progress)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
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
