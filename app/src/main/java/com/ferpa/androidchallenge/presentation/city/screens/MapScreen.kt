package com.ferpa.androidchallenge.presentation.city.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ferpa.androidchallenge.R
import com.ferpa.androidchallenge.presentation.city.CityViewModel
import com.ferpa.androidchallenge.presentation.utils.isLandscape
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onBackPressed: (() -> Unit)? = null,
    viewModel: CityViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val city by viewModel.selectedCity.collectAsState()

    val isPortrait = !isLandscape()

    val cameraPositionState = remember { CameraPositionState() }

    var showErrorDialog by remember { mutableStateOf(false) }


    LaunchedEffect(city) {
        try {
            city?.let {
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(it.coord.lat, it.coord.lon),
                        7f
                    )
                )
            }
        } catch (e: Exception) {
            showErrorDialog = true
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        if (isPortrait) {
            TopAppBar(
                title = { city?.name?.let { Text(city!!.name) } },
                navigationIcon = {
                    IconButton(onClick = {
                        if (onBackPressed != null) {
                            onBackPressed()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            city?.let {
                Marker(
                    state = MarkerState(
                        position = LatLng(it.coord.lat, it.coord.lon)
                    ),
                    title = it.name,
                    snippet = it.country
                )
            }
        }

        if (showErrorDialog) {
            ErrorDialog(
                onDismiss = { showErrorDialog = false },
                onRetry = {
                    showErrorDialog = false
                    // Trigger reload logic, if applicable
                }
            )
        }
    }
}

@Composable
fun ErrorDialog(
    onDismiss: () -> Unit,
    onRetry: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(id = R.string.error_dialog_title)) },
        text = {
            Text(stringResource(id = R.string.error_dialog_message))
        },
        confirmButton = {
            Button(onClick = onRetry) {
                Text(stringResource(id = R.string.error_dialog_accept_button))
            }
        }
    )
}