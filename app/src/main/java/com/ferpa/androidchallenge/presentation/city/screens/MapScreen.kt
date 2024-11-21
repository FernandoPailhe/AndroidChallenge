package com.ferpa.androidchallenge.presentation.city.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ferpa.androidchallenge.presentation.city.CityViewModel
import com.ferpa.androidchallenge.presentation.city.MapViewModel
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    navController: NavController,
    viewModel: CityViewModel = hiltViewModel()
) {
    val city by viewModel.selectedCity.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        city?.let {
            position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
                com.google.android.gms.maps.model.LatLng(it.coord.lat, it.coord.lon),
                10f
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        city?.let {
            Marker(
                state = MarkerState(
                    position = com.google.android.gms.maps.model.LatLng(it.coord.lat, it.coord.lon)
                ),
                title = it.name,
                snippet = it.country
            )
        }
    }
}