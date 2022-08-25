package co.hatch.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import co.hatch.data.DeviceDetailsArgumentModel
import co.hatch.deviceClientLib.model.Device
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavArguments @Inject constructor() {

    var deviceDetailsArgument by mutableStateOf<DeviceDetailsArgumentModel?>(null)
        private set

    fun setDeviceDetailsArguments(device: Device?) {
        deviceDetailsArgument = if (device != null) {
            DeviceDetailsArgumentModel(device = device)
        } else {
            null
        }
    }

    fun clearAllArguments() {
        deviceDetailsArgument = null
    }

}