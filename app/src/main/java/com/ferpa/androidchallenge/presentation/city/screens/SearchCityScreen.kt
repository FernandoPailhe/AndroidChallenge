package com.ferpa.androidchallenge.presentation.city.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ferpa.androidchallenge.R
import com.ferpa.androidchallenge.presentation.Screen
import com.ferpa.androidchallenge.presentation.city.CityViewModel
import com.ferpa.androidchallenge.presentation.city.components.CityItem
import com.ferpa.androidchallenge.presentation.common.SearchBar
import com.ferpa.androidchallenge.presentation.utils.isLandscape
import com.ferpa.androidchallenge.remote.dto.City
import androidx.compose.ui.zIndex
import androidx.paging.LoadState

@Composable
fun SearchCityScreen(
    onCitySelected: ((City) -> Unit)? = null,
    navController: NavController,
    viewModel: CityViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {

    val horizontalPadding = if (isLandscape()) 0.dp else 8.dp
    val topPadding = if (isLandscape()) 0.dp else 8.dp

    val query by viewModel.query.collectAsState()

    val onlyFavorites by viewModel.onlyFavorites.collectAsState()
    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()

    val isLoading = searchResults.loadState.refresh is LoadState.Loading

    Box(modifier = modifier.fillMaxSize()) {
        CurvedHeader(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp)
                .zIndex(0f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
        ) {
            SearchBar(
                query = query,
                onQueryChanged = { viewModel.updateQuery(it) },
                modifier = Modifier.padding(
                    start = horizontalPadding,
                    end = horizontalPadding,
                    bottom = 0.dp,
                    top = topPadding,
                ),
                placeholder = stringResource(id = R.string.search_cities_placeholder)
            )
            Row(
                modifier = Modifier
                    .clickable { viewModel.toggleFavoritesFilter() }
                    .padding(horizontal = 8.dp, vertical = 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = onlyFavorites == true,
                    onCheckedChange = { viewModel.toggleFavoritesFilter() }
                )
                Text(
                    text = stringResource(id = R.string.show_favorites),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            if (searchResults.itemCount == 0 && !isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.empty_state),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else if (searchResults.itemCount > 0){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(searchResults.itemCount) { index ->
                            val city = searchResults[index]
                            if (city !== null) {
                                CityItem(
                                    index = index,
                                    city = city,
                                    onCardPress = {
                                        viewModel.selectCity(city)
                                        if (onCitySelected != null) {
                                            onCitySelected(city)
                                        }
                                    },
                                    onFavoritePress = {
                                        viewModel.toggleIsFavorite(city)
                                    },
                                    onInfoPress = {
                                        viewModel.selectCity(city)
                                        navController.navigate(Screen.CityDetailScreenRoute.route)
                                    }
                                )
                            }
                        }
                    }
                } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun CurvedHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(30.dp)
                .absoluteOffset(y = 30.dp)
                .background(MaterialTheme.colorScheme.surface)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 30.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(MaterialTheme.colorScheme.primary)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 50.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface.copy(0.95f),
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                )
        )
    }
}
