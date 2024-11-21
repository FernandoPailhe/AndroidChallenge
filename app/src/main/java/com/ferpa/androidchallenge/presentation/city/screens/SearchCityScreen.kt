package com.ferpa.androidchallenge.presentation.city.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ferpa.androidchallenge.presentation.Screen
import com.ferpa.androidchallenge.presentation.city.CityViewModel

@Composable
fun SearchCityScreen(
    navController: NavController,
    viewModel: CityViewModel = hiltViewModel()
) {

    val pagedCities = viewModel.pagedCities.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(pagedCities.itemCount) { index ->
            val city = pagedCities[index]
            if (city !== null) {
                Text(
                    text = "${city.name}, ${city.country}",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            viewModel.selectCity(city)
                            navController.navigate(Screen.CityDetailScreenRoute.route)
                        }
                )
            }
        }
    }

    when (pagedCities.loadState.append) {
        is LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is LoadState.Error -> {
            Text(
                text = "Error loading more data",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error
            )
        }

        else -> {}
    }

}
