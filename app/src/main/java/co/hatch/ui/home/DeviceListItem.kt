package co.hatch.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.hatch.R
import co.hatch.deviceClientLib.model.Device
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
internal fun DeviceListItem(
	device: Device,
	onDeviceClicked: (Device) -> Unit = {}
) {

	Column(modifier = Modifier.fillMaxWidth().clickable {
		onDeviceClicked(device)
	}) {
		Text(
			text = device.name,
			style = MaterialTheme.typography.bodyMedium
		)

		Text(
			text = stringResource(id = R.string.rssi,
				device.rssi
			),
			style = MaterialTheme.typography.bodyMedium
		)

		Text(
			text = stringResource(id = R.string.last_connected,
				getLastConnectedTime(device.latestConnectedTime)
			),
			style = MaterialTheme.typography.bodyMedium,
			modifier = Modifier.padding(bottom = 16.dp)
		)
	}
}

fun getLastConnectedTime(date: Date?): String {
	val formatter = SimpleDateFormat("EEE, MMM d, yyyy 'at' HH:mm:ss z", Locale.getDefault())
	return if (date != null) {
		formatter.format(date)
	} else {
		"N/A"
	}
}