package co.hatch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.hatch.screens.details.DetailsScreen
import co.hatch.screens.home.HomeScreen

@Composable
fun Root(isDarkTheme: MutableState<Boolean>) {
    val navController = LocalMainNavController.current

    NavHost(
        navController,
        startDestination = Screen.Root.Home.route,
        route = ROOT_ROUTE
    ) {
        composable(Screen.Root.Home.route) {
            HomeScreen(isDarkTheme = isDarkTheme)
        }
        composable(Screen.Root.Home.route) {
            DetailsScreen(isDarkTheme = isDarkTheme)
        }
    }

}