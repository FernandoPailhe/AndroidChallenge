package com.ferpa.androidchallenge.domain.businesslogic


import com.ferpa.androidchallenge.domain.repository.CityRepository
import com.ferpa.androidchallenge.remote.dto.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject


class SearchCitiesUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {
    operator fun invoke(query: String, onlyFavorites: Boolean? = null): Flow<List<City>> {
        return if (query.isBlank()) {
            flowOf(emptyList())
        } else {
            cityRepository.searchCities(query, onlyFavorites)
        }
    }
}