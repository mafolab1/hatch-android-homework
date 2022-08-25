package co.hatch.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.hatch.R
import co.hatch.deviceClientLib.model.Device
import co.hatch.ui.common.formatLastConnectedTime

@Composable
internal fun DeviceListItem(
    device: Device,
    onDeviceClicked: (Device) -> Unit = {}
) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        .clickable {
            onDeviceClicked(device)
        }) {
        Text(
            text = device.name,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = stringResource(
                id = R.string.rssi,
                device.rssi
            ),
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = stringResource(
                id = R.string.last_connected,
                formatLastConnectedTime(device.latestConnectedTime)
            ),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}