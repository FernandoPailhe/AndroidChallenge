package com.ferpa.androidchallenge.domain.businesslogic
import androidx.paging.PagingSource
import androidx.paging.PagingData
import app.cash.turbine.test
import com.ferpa.androidchallenge.domain.repository.CityRepository
import com.ferpa.androidchallenge.remote.dto.City
import com.ferpa.androidchallenge.remote.dto.Coordinates
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchCitiesUseCaseTest {

    private val mockCityRepository = mockk<CityRepository>()

    private val searchCitiesUseCase = SearchCitiesUseCase(mockCityRepository)

    private val sampleCities = listOf(
        City(1, "New York", "USA", Coordinates(-74.006, 40.7128)),
        City(2, "Los Angeles", "USA", Coordinates(-118.2437, 34.0522)),
        City(3, "London", "UK", Coordinates(-0.1276, 51.5074)),
        City(4, "Paris", "France", Coordinates(2.3522, 48.8566))
    )

    @Test
    fun `invoke with empty query returns all cities`() = runTest {
        // Mock repository response
        val expectedPagingData = PagingData.from(sampleCities)
        coEvery { mockCityRepository.getPagedCities(null) } returns flowOf(expectedPagingData)

        // Use case invocation
        val result = searchCitiesUseCase("", null)

        // Assert emitted result
        result.test {
            assertEquals(expectedPagingData, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke with query filters cities correctly`() = runTest {
        // Mock repository response
        val filteredCities = sampleCities.filter { it.name.contains("New", ignoreCase = true) }
        val expectedPagingData = PagingData.from(filteredCities)
        coEvery { mockCityRepository.searchCitiesPaginated("New", null) } returns flowOf(expectedPagingData)

        // Use case invocation
        val result = searchCitiesUseCase("New", null)

        // Assert emitted result
        result.test {
            assertEquals(expectedPagingData, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke with invalid query returns empty result`() = runTest {
        // Mock repository response
        val emptyPagingData = PagingData.empty<City>()
        coEvery { mockCityRepository.searchCitiesPaginated("xyz", null) } returns flowOf(emptyPagingData)

        // Use case invocation
        val result = searchCitiesUseCase("xyz", null)

        // Assert emitted result
        result.test {
            assertEquals(emptyPagingData, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke with query and onlyFavorites returns filtered results`() = runTest {
        // Mock repository response
        val filteredCities = sampleCities.filter { it.isFavorite && it.name.contains("Paris", ignoreCase = true) }
        val expectedPagingData = PagingData.from(filteredCities)
        coEvery { mockCityRepository.searchCitiesPaginated("Paris", true) } returns flowOf(expectedPagingData)

        // Use case invocation
        val result = searchCitiesUseCase("Paris", true)

        // Assert emitted result
        result.test {
            assertEquals(expectedPagingData, awaitItem())
            awaitComplete()
        }
    }
}