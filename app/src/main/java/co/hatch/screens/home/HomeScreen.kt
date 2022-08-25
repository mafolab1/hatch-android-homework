package co.hatch.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import co.hatch.navigation.LocalMainNavController

@Composable
fun HomeScreen(isDarkTheme: MutableState<Boolean>) {
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val state by viewModel.uiState.collectAsState()

    HomeScreen(
        viewState = state,
        viewModel = viewModel,
        navController = LocalMainNavController.current
    )
}

@Composable
private fun HomeScreen(
    viewState: HomeScreenState,
    viewModel: HomeScreenViewModel,
    navController: NavController
) {

    when (viewState) {
        is HomeScreenState.Loading -> {

        }
        else -> {}
    }


}