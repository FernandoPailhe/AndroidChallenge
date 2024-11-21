package com.ferpa.androidchallenge.presentation

/**
 * A sealed class representing the navigation routes for the app.
 * Each route corresponds to a specific screen in the application.
 *
 * @property route The string identifier used to navigate to the corresponding screen.
 */
sealed class Screen(val route: String) {
    data object SearchCityScreenRoute : Screen("search_city_screen")
    data object CityDetailScreenRoute : Screen("city_detail_screen")
}