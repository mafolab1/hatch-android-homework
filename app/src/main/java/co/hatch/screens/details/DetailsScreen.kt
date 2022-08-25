package co.hatch.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import co.hatch.navigation.LocalMainNavController
import co.hatch.screens.home.HomeScreenState
import co.hatch.screens.home.HomeScreenViewModel

@Composable
fun DetailsScreen(isDarkTheme: MutableState<Boolean>) {
    val viewModel = hiltViewModel<DetailsScreenViewModel>()
    val state by viewModel.uiState.collectAsState()

    DetailsScreen(
        viewState = state,
        viewModel = viewModel,
        navController = LocalMainNavController.current
    )
}

@Composable
private fun DetailsScreen(
    viewState: DetailsScreenState,
    viewModel: DetailsScreenViewModel,
    navController: NavController
) {

    when (viewState) {
        is DetailsScreenState.Loading -> {

        }
        else -> {}
    }


}