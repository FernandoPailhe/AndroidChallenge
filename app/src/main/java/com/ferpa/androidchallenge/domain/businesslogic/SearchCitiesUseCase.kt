package com.ferpa.androidchallenge.domain.businesslogic


import androidx.paging.PagingData
import com.ferpa.androidchallenge.domain.repository.CityRepository
import com.ferpa.androidchallenge.remote.dto.City
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for searching cities based on a query and an optional filter for favorite cities.
 *
 * This use case determines whether to perform a search query or fetch the full paginated list of cities
 * based on whether the `query` is blank. If a `query` is provided, it fetches a paginated list of cities
 * matching the query. Otherwise, it fetches all paginated cities, optionally filtered by their favorite status.
 *
 * @property cityRepository The repository responsible for interacting with city data.
 *
 * @constructor Creates an instance of [SearchCitiesUseCase].
 *
 * @param query A string used to search for cities by name or country. If blank, returns all cities.
 * @param onlyFavorites A nullable boolean indicating if only favorite cities should be included:
 *                      - `true`: Includes only favorite cities.
 *                      - `false`: Includes only non-favorite cities.
 *                      - `null`: Includes all cities regardless of favorite status.
 *
 * @return A [Flow] emitting [PagingData] containing the list of [City] objects matching the criteria.
 */
class SearchCitiesUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {
    operator fun invoke(query: String, onlyFavorites: Boolean? = null): Flow<PagingData<City>> {
        return if (query.isBlank()) {
            cityRepository.getPagedCities(onlyFavorites)
        } else {
            cityRepository.searchCitiesPaginated(query, onlyFavorites)
        }
    }
}