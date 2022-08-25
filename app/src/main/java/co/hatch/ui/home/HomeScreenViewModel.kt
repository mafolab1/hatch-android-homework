package co.hatch.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import co.hatch.application.IoDispatcher
import co.hatch.deviceClientLib.connectivity.ConnectivityClient
import co.hatch.deviceClientLib.model.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val connectivityClient: ConnectivityClient) : ViewModel() {

    @Inject
    @IoDispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    private val _uiState = MutableStateFlow<HomeScreenState>(HomeScreenState.Default)
    val uiState = _uiState.asStateFlow()

    private val _discoveredDevices = mutableStateListOf<Device>()
    val discoveredDevices: SnapshotStateList<Device> = _discoveredDevices

    fun resetViewState() {
        _uiState.value = HomeScreenState.Default
    }

    fun discoverDevices() {
        _uiState.value = HomeScreenState.Loading
        _discoveredDevices.clear()
        CoroutineScope(ioDispatcher).launch {
            _discoveredDevices.addAll(connectivityClient.discoverDevices().sortedByDescending { it.rssi })
        }
        resetViewState()

        Log.e("<><>:", _discoveredDevices.toString())
    }

    suspend fun isAuthenticated() {
//		if(settings.getDeviceId)
    }
}