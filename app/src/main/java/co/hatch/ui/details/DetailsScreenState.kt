package co.hatch.ui.details

sealed class DetailsScreenState {
    object Default : DetailsScreenState()
    object Loading : DetailsScreenState()
    object Success : DetailsScreenState()
    data class Error(val errorMessage: String? = null) : DetailsScreenState()
}