package com.ferpa.androidchallenge.presentation.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ferpa.androidchallenge.domain.repository.CityRepository
import com.ferpa.androidchallenge.remote.dto.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val cityRepository: CityRepository
) : ViewModel() {

    private val _selectedCity = MutableStateFlow<City?>(null)
    val selectedCity: StateFlow<City?> = _selectedCity

    init {
        downloadAndSaveCities()
    }

    val pagedCities: Flow<PagingData<City>> = cityRepository.getPagedCities()
        .cachedIn(viewModelScope)

    fun selectCity(city: City) {
        _selectedCity.value = city
    }

    private fun downloadAndSaveCities() {
        viewModelScope.launch(Dispatchers.IO) {
            cityRepository.fetchAndStoreCities()
        }
    }

}

