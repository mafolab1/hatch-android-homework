package co.hatch.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalMainNavController = compositionLocalOf<NavHostController> { error("No nav controller") }