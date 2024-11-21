package com.ferpa.androidchallenge.presentation.city

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ferpa.androidchallenge.common.UiState
import com.ferpa.androidchallenge.domain.businesslogic.SearchCitiesUseCase
import com.ferpa.androidchallenge.domain.repository.CityRepository
import com.ferpa.androidchallenge.remote.dto.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val cityRepository: CityRepository,
    private val searchCitiesUseCase: SearchCitiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Int>>(UiState.Loading)
    val uiState: StateFlow<UiState<Int>> = _uiState

    private val _selectedCity = MutableStateFlow<City?>(null)
    val selectedCity: StateFlow<City?> = _selectedCity

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    val searchResults: StateFlow<List<City>> = query
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            searchCitiesUseCase(query)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        downloadAndSaveCities()
    }

    val pagedCities: Flow<PagingData<City>> = cityRepository.getPagedCities()
        .cachedIn(viewModelScope)

    fun selectCity(city: City) {
        _selectedCity.value = city
    }

    fun toggleIsFavorite(city: City) {

    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
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

