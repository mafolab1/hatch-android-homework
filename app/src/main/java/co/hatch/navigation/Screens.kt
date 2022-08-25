package co.hatch.navigation

const val ROOT_ROUTE = "root"

sealed class Screen(val route: String) {
    object Home : Screen(route = "home_route")
    object Details : Screen(route = "details_route")


}
