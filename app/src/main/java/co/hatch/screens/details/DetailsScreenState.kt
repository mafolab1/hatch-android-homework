package co.hatch.screens.details

sealed class DetailsScreenState {
    object Default : DetailsScreenState()
    object Loading : DetailsScreenState()
}