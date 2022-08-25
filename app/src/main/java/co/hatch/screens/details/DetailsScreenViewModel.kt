package co.hatch.screens.details

import androidx.lifecycle.ViewModel
import co.hatch.deviceClientLib.connectivity.ConnectivityClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(private val connectivityClient: ConnectivityClient) : ViewModel() {

    private val mutableState = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Default)
    val uiState = mutableState.asStateFlow()

    fun resetViewState() {
        mutableState.value = DetailsScreenState.Default
    }

    suspend fun isAuthenticated() {
//		if(settings.getDeviceId)
    }
}