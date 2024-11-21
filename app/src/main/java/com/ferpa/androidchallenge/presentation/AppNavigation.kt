package com.ferpa.androidchallenge.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ferpa.androidchallenge.presentation.city.CityViewModel
import com.ferpa.androidchallenge.presentation.city.screens.CityDetailScreen
import com.ferpa.androidchallenge.presentation.city.screens.CityMainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: CityViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screen.CityMainScreenRoute.route) {
        composable(route = Screen.CityMainScreenRoute.route) {
            CityMainScreen(navController, viewModel)
        }
        composable(route = Screen.CityDetailScreenRoute.route) {
            CityDetailScreen(navController, viewModel)
        }
    }
}