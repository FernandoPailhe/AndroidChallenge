package com.ferpa.androidchallenge.domain.businesslogic

import com.ferpa.androidchallenge.domain.repository.CityRepository
import com.ferpa.androidchallenge.remote.dto.City
import javax.inject.Inject


class ToggleFavoriteCityUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {
    suspend operator fun invoke(city: City) {
        val updatedCity = city.copy(isFavorite = !city.isFavorite)
        cityRepository.updateCity(updatedCity)
    }
}