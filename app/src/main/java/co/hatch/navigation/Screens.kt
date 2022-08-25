package co.hatch.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Roofing
import androidx.compose.material.icons.rounded.ModeOfTravel
import androidx.compose.ui.graphics.vector.ImageVector

const val ROOT_ROUTE = "root"

sealed class Screen(val route: String) {
    object Root {
        object Home : Screen(route = "home_route")
        object Details : Screen(route = "details_route")
    }

    }
