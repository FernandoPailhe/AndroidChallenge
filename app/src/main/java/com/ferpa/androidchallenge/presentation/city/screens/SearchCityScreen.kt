package com.ferpa.androidchallenge.presentation.city.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ferpa.androidchallenge.presentation.Screen
import com.ferpa.androidchallenge.presentation.city.CityViewModel
import com.ferpa.androidchallenge.presentation.city.components.CityItem
import com.ferpa.androidchallenge.presentation.common.SearchBar

@Composable
fun SearchCityScreen(
    navController: NavController,
    viewModel: CityViewModel = hiltViewModel()
) {

    val query by viewModel.query.collectAsState()

    val searchResults by viewModel.searchResults.collectAsState()
    val pagedCities = viewModel.pagedCities.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = query,
            onQueryChanged = { viewModel.updateQuery(it) },
            placeholder = "Search cities..."
        )
        if (query == "") {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(pagedCities.itemCount) { index ->
                    val city = pagedCities[index]
                    if (city !== null) {
                        CityItem(
                            index = index,
                            city = city,
                            onCardPress = {
                                viewModel.selectCity(city)
                                navController.navigate(Screen.MapScreenRoute.route)
                            },
                            onFavoritePress = {
                                viewModel.toggleIsFavorite(city)
                            },
                            onInfoPress = {
                                viewModel.selectCity(city)
                                navController.navigate(Screen.CityDetailScreenRoute.route)
                            })
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(searchResults) { city ->
                    city?.let {
                        CityItem(
                            index = searchResults.indexOf(city),
                            city = city,
                            onCardPress = {
                                viewModel.selectCity(city)
                            },
                            onFavoritePress = { /* Handle favorite */ },
                            onInfoPress = {
                                viewModel.selectCity(city)
                                navController.navigate(Screen.CityDetailScreenRoute.route)
                            }
                        )
                    }
                }
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
