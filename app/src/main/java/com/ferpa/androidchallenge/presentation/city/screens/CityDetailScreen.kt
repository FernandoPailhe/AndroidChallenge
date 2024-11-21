package com.ferpa.androidchallenge.presentation.city.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ferpa.androidchallenge.R
import com.ferpa.androidchallenge.presentation.city.CityViewModel

@Composable
fun CityDetailScreen(
    navController: NavController,
    viewModel: CityViewModel = hiltViewModel()
) {

    val city by viewModel.selectedCity.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.city_detail_disclaimer),
            color = MaterialTheme.colorScheme.onSurface
        )
        if (city != null) {
            Text(
                text = "${stringResource(id = R.string.city)}${city?.name}",
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${stringResource(id = R.string.country_code)}${city?.country}",
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(onClick = { navController.popBackStack() }) {
            Text(text = stringResource(id = R.string.back_button))
        }
    }

}
