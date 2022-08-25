package co.hatch.ui.details

import android.util.Log
import androidx.compose.runtime.mutableStateOf
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
class DetailsScreenViewModel @Inject constructor(private val connectivityClient: ConnectivityClient, private val navArguments: NavArguments) : ViewModel() {

	@Inject
	@IoDispatcher
	lateinit var ioDispatcher: CoroutineDispatcher

	private val _uiState = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Default)
	val uiState = _uiState.asStateFlow()

	var deviceName = mutableStateOf(getDeviceDetailsArgument()?.name ?: "")
	val deviceNameTextFieldErrorState = mutableStateOf(false)

	val device = mutableStateOf(getDeviceDetailsArgument())

	fun resetViewState() {
		_uiState.value = DetailsScreenState.Default
	}

	fun setDeviceDetailsArgument(device: Device?) {
		navArguments.setDeviceDetailsArguments(null)
	}

	fun getDeviceDetailsArgument(): Device? {
		return navArguments.deviceDetailsArgument?.device
	}

	fun verifyDeviceName(name: String) {
		Log.e("<><", "device name: $name")
		if (name.isBlank() || name.trim().length < 3) {
			deviceNameTextFieldErrorState.value = true
		}
	}

	fun changeDeviceName() {
		val name = deviceName.value
		if (name.isNotBlank() && name.trim().length > 3) {
			_uiState.value = DetailsScreenState.Loading
			CoroutineScope(ioDispatcher).launch {
				if (connectivityClient.updateDeviceName(device.value!!.id, name)) {
					//show success snackbar
					setDeviceDetailsArgument(Device(device.value!!.id, name, device.value!!.rssi, device.value!!.connected, device.value!!.elapsedSecsConnected, device.value!!.latestConnectedTime))
				} else {
					/// showErrorDialog
				}
			}
		} else {
			deviceNameTextFieldErrorState.value = true
		}
	}

	fun connectDevice() {
		_uiState.value = DetailsScreenState.Loading
		CoroutineScope(ioDispatcher).launch {
			connectivityClient.connectToDeviceBy(device.value?.id ?: "", object : ConnectivityClient.OnDeviceStateChangeListener {
				override fun onDeviceStateChanged(deviceId: String, device: Device) {
					this@DetailsScreenViewModel.device.value = device
					Log.e("<><>", "hehehehe $device \n dsd \n ${this@DetailsScreenViewModel.device.value}")
					//TODO: end loading spiner show susccess snackbar
				}

			})
			resetViewState()
		}
	}

	fun disconnectDevice() {
		//TODO: end loading spiner show susccess snackbar

		_uiState.value = DetailsScreenState.Loading
		CoroutineScope(ioDispatcher).launch {
			if (connectivityClient.disconnectFromDevice(device.value?.id ?: "")) {
				resetViewState()
			} else {
				//reset state
			}

		}


	}

}