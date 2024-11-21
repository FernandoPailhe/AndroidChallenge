package com.ferpa.androidchallenge.presentation.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ferpa.androidchallenge.common.UiState
import com.ferpa.androidchallenge.domain.businesslogic.SearchCitiesUseCase
import com.ferpa.androidchallenge.domain.businesslogic.ToggleFavoriteCityUseCase
import com.ferpa.androidchallenge.domain.repository.CityRepository
import com.ferpa.androidchallenge.remote.dto.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val cityRepository: CityRepository,
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val toggleFavoriteCityUseCase: ToggleFavoriteCityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Int>>(UiState.Loading)
    val uiState: StateFlow<UiState<Int>> = _uiState

    private val _selectedCity = MutableStateFlow<City?>(null)
    val selectedCity: StateFlow<City?> = _selectedCity

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _onlyFavorites = MutableStateFlow<Boolean?>(null)
    val onlyFavorites: StateFlow<Boolean?> = _onlyFavorites

    // Unified paginated results with or without search query
    val searchResults: Flow<PagingData<City>> = combine(
        query.debounce(300).distinctUntilChanged(),
        _onlyFavorites
    ) { query, onlyFavorites ->
        query to onlyFavorites
    }
        .flatMapLatest { (query, onlyFavorites) ->
            searchCitiesUseCase(query, onlyFavorites)
        }
        .cachedIn(viewModelScope)

    init {
        downloadAndSaveCities()
    }

    fun selectCity(city: City) {
        _selectedCity.value = city
    }

    fun toggleIsFavorite(city: City) {
        viewModelScope.launch {
            toggleFavoriteCityUseCase(city)
        }
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun toggleFavoritesFilter() {
        _onlyFavorites.value = if (_onlyFavorites.value == true) null else true
    }

    private fun downloadAndSaveCities() {
        viewModelScope.launch {
            cityRepository.fetchAndStoreCities { progress ->
                _uiState.value = UiState.Success(progress)
            }
            _uiState.value = UiState.Success(100)
        }
    }

}

