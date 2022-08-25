package co.hatch.screens.home

sealed class HomeScreenState {
    object Default : HomeScreenState()
    object Loading : HomeScreenState()
}