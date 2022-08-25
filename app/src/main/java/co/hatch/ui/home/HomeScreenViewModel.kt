package co.hatch.ui.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import co.hatch.application.IoDispatcher
import co.hatch.deviceClientLib.connectivity.ConnectivityClient
import co.hatch.deviceClientLib.model.Device
import co.hatch.navigation.NavArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val connectivityClient: ConnectivityClient, private val navArguments: NavArguments) : ViewModel() {

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
		CoroutineScope(ioDispatcher).launch {
			_discoveredDevices.clear()
			_discoveredDevices.addAll(connectivityClient.discoverDevices().sortedByDescending { it.rssi })
			resetViewState()
		}
	}

	fun setDeviceDetailsArgument(device: Device) {
		navArguments.setDeviceDetailsArguments(device)
	}
}