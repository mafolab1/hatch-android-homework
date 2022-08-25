package co.hatch.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.hatch.data.DeviceDetailsArgumentModel
import co.hatch.deviceClientLib.connectivity.ConnectivityClient
import co.hatch.deviceClientLib.model.Device
import co.hatch.navigation.NavArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(private val connectivityClient: ConnectivityClient, private val navArguments: NavArguments) : ViewModel() {

    private val mutableState = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Default)
    val uiState = mutableState.asStateFlow()

    var deviceName =  mutableStateOf(getDeviceDetailsArgument()?.name ?: "")
    val deviceNameTextFieldErrorState = mutableStateOf(false)

    fun resetViewState() {
        mutableState.value = DetailsScreenState.Default
    }

    fun setDeviceDetailsArgument(device: Device?) {
        navArguments.setDeviceDetailsArguments(null)
    }

    fun getDeviceDetailsArgument(): Device? {
        return navArguments.deviceDetailsArgument?.device
    }

}