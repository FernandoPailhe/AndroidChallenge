package com.ferpa.androidchallenge.presentation.city.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ferpa.androidchallenge.common.UiState
import com.ferpa.androidchallenge.presentation.city.CityViewModel
import com.ferpa.androidchallenge.presentation.utils.isLandscape

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CityMainScreen(
    navController: NavController,
    viewModel: CityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success -> {
            val progress = (uiState as UiState.Success<Int>).data
            if (progress < 100) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Downloading data: $progress%", modifier = Modifier.padding(16.dp))
                        LinearProgressIndicator(progress = progress / 100f)
                    }
                }
            } else {
                val isLandscape = isLandscape()

                if (isLandscape) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        SearchCityScreen(
                            modifier = Modifier.weight(.4f).fillMaxHeight(),
                            navController = navController,
                            viewModel = viewModel,
                        )
                        MapScreen(
                            modifier = Modifier.weight(.6f).fillMaxHeight(),
                            viewModel = viewModel,
                        )
                    }
                } else {
                    Box {
                        var showMap by remember { mutableStateOf(false) }

                        AnimatedContent(
                            targetState = showMap,
                            transitionSpec = {
                                if (targetState) {
                                    slideInHorizontally { it } + fadeIn() with slideOutHorizontally { -it } + fadeOut()
                                } else {
                                    slideInHorizontally { -it } + fadeIn() with slideOutHorizontally { it } + fadeOut()
                                }
                            }, label = ""
                        ) { target ->
                            if (target) {
                                MapScreen(
                                    onBackPressed = { showMap = false },
                                    viewModel = viewModel,
                                )
                            } else {
                                SearchCityScreen(
                                    onCitySelected = { city ->
                                        showMap = true
                                    },
                                    navController = navController,
                                    viewModel = viewModel,
                                )
                            }
                        }
                    }
                }
//                CityContent(navController = navController, viewModel = viewModel)
            }
        }
        is UiState.Error -> {
            val message = (uiState as UiState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: $message", color = Color.Red)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CityContent(
    navController: NavController,
    viewModel: CityViewModel
) {
    val isLandscape = isLandscape()

    if (isLandscape) {
        Row(modifier = Modifier.fillMaxSize()) {
            SearchCityScreen(
                modifier = Modifier.weight(.4f).fillMaxHeight(),
                navController = navController,
                viewModel = viewModel,
            )
            MapScreen(
                modifier = Modifier.weight(.6f).fillMaxHeight(),
                viewModel = viewModel,
            )
        }
    } else {
        Box {
            var showMap by remember { mutableStateOf(false) }

            AnimatedContent(
                targetState = showMap,
                transitionSpec = {
                    if (targetState) {
                        slideInHorizontally { it } + fadeIn() with slideOutHorizontally { -it } + fadeOut()
                    } else {
                        slideInHorizontally { -it } + fadeIn() with slideOutHorizontally { it } + fadeOut()
                    }
                }, label = ""
            ) { target ->
                if (target) {
                    MapScreen(
                        onBackPressed = { showMap = false },
                        viewModel = viewModel,
                    )
                } else {
                    SearchCityScreen(
                        onCitySelected = { city ->
                            showMap = true
                        },
                        navController = navController,
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}
