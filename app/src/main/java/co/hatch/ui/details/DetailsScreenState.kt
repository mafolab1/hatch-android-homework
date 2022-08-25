package co.hatch.ui.details

sealed class DetailsScreenState {
    object Default : DetailsScreenState()
    object Loading : DetailsScreenState()
}