package co.hatch.ui.home

sealed class HomeScreenState {
    object Default : HomeScreenState()
    object Loading : HomeScreenState()
    data class Error(val errorMessage: String? = null) : HomeScreenState()
}