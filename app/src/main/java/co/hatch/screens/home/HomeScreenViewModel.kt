package co.hatch.screens.home

import androidx.lifecycle.ViewModel
import co.hatch.deviceClientLib.connectivity.ConnectivityClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val connectivityClient: ConnectivityClient) : ViewModel() {

    private val mutableState = MutableStateFlow<HomeScreenState>(HomeScreenState.Default)
    val uiState = mutableState.asStateFlow()

    fun resetViewState() {
        mutableState.value = HomeScreenState.Default
    }

    suspend fun isAuthenticated() {
//		if(settings.getDeviceId)
    }
}