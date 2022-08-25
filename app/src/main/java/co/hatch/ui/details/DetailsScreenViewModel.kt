package co.hatch.ui.details

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import co.hatch.application.IoDispatcher
import co.hatch.application.MainDispatcher
import co.hatch.deviceClientLib.connectivity.ConnectivityClient
import co.hatch.deviceClientLib.model.Device
import co.hatch.navigation.NavArguments
import co.hatch.ui.common.SnackbarVisualsWithError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val connectivityClient: ConnectivityClient,
    private val navArguments: NavArguments
) : ViewModel() {

    @Inject
    @IoDispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    @Inject
    @MainDispatcher
    lateinit var mainDispatcher: CoroutineDispatcher

    private val _uiState = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Default)
    val uiState = _uiState.asStateFlow()


    val device = mutableStateOf(navArguments.deviceDetailsArgument?.device)
    var deviceName = mutableStateOf(navArguments.deviceDetailsArgument?.device?.name ?: "")
    val deviceNameTextFieldErrorState = mutableStateOf(false)

    fun resetViewState() {
        _uiState.value = DetailsScreenState.Default
    }


    fun clearNavArgs() {
        navArguments.clearAllArguments()
    }

    fun verifyDeviceName(name: String) {
        if (name.isBlank() || name.trim().length < 3) {
            deviceNameTextFieldErrorState.value = true
        }
    }

    fun changeDeviceName() {
        val name = deviceName.value
        if (name.isNotBlank() && name.trim().length > 3) {
            _uiState.value = DetailsScreenState.Loading
            CoroutineScope(ioDispatcher).launch {
                try {
                    if (connectivityClient.updateDeviceName(device.value!!.id, name)) {
                        _uiState.value = DetailsScreenState.Success
                        connectivityClient.discoverDevices()
                    } else {
                        _uiState.value = DetailsScreenState.Error()
                    }
                } catch (e: Exception) {
                    _uiState.value = DetailsScreenState.Error()
                }

            }
        } else {
            deviceNameTextFieldErrorState.value = true
        }
    }

    fun connectDevice() {
        _uiState.value = DetailsScreenState.Loading
        CoroutineScope(ioDispatcher).launch {
            if (!connectivityClient.connectToDeviceBy(
                    device.value?.id ?: "",
                    object : ConnectivityClient.OnDeviceStateChangeListener {
                        override fun onDeviceStateChanged(deviceId: String, device: Device) {
                            if (deviceId == this@DetailsScreenViewModel.device.value!!.id) {
                                this@DetailsScreenViewModel.device.value = device
                            }
                            Log.d(
                                "<><>",
                                "Device refreshed info: $device \n dsd \n ${this@DetailsScreenViewModel.device.value}"
                            )
                        }

                    })
            ) {
                _uiState.value =
                    DetailsScreenState.Error("Cannot connect to device, try again later.")
            }
            resetViewState()
        }
    }

    fun disconnectDevice() {
        _uiState.value = DetailsScreenState.Loading
        CoroutineScope(ioDispatcher).launch {
            if (connectivityClient.disconnectFromDevice(device.value?.id ?: "")) {
                deviceName.value = device.value?.name ?: ""
                resetViewState()
            } else {
                _uiState.value =
                    DetailsScreenState.Error("Cannot disconnect device, try again later.")
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun showSnackBar(
        snackbarHostState: SnackbarHostState,
        isError: Boolean,
        message: String? = null
    ) {
        CoroutineScope(mainDispatcher).launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            if (isError) {
                snackbarHostState.showSnackbar(
                    SnackbarVisualsWithError(
                        message = message ?: "Device name could not be updated, try again later.",
                        isError = true,
                    )
                )
            } else {
                snackbarHostState.showSnackbar(
                    message = "Device name updated",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

}