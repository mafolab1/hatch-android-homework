package co.hatch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.hatch.ui.details.DetailsScreen
import co.hatch.ui.home.HomeScreen

@Composable
fun Root(navController: NavHostController) {

	NavHost(
		navController,
		startDestination = Screen.Home.route,
		route = ROOT_ROUTE
	) {
		composable(Screen.Home.route) {
			HomeScreen()
		}
		composable(Screen.Details.route) {
			DetailsScreen()
		}
	}

}