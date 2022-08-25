package co.hatch.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.hatch.R
import co.hatch.ui.common.TopBar

@Composable
fun DetailsScreen() {
	val viewModel = hiltViewModel<DetailsScreenViewModel>()
	val state by viewModel.uiState.collectAsState()

	DetailsScreen(
		viewState = state,
		viewModel = viewModel
	)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun DetailsScreen(
	viewState: DetailsScreenState,
	viewModel: DetailsScreenViewModel
) {

	val device = remember {
		viewModel.device
	}

	val snackbarHostState = remember { SnackbarHostState() }
	val scope = rememberCoroutineScope()
	val deviceNameError = remember { (viewModel.deviceNameTextFieldErrorState) }
	val focusManager = LocalFocusManager.current

	Scaffold(
		topBar = { TopBar(stringResource(id = R.string.device_details), true) },
		snackbarHost = {
			// reuse default SnackbarHost to have default animation and timing handling
			SnackbarHost(snackbarHostState) { data ->
				// custom snackbar with the custom action button color and border
				val isError = false
				val buttonColor = if (isError) {
					ButtonDefaults.textButtonColors(
						containerColor = MaterialTheme.colorScheme.errorContainer,
						contentColor = MaterialTheme.colorScheme.error
					)
				} else {
					ButtonDefaults.textButtonColors(
						contentColor = MaterialTheme.colorScheme.inversePrimary
					)
				}

				Snackbar(
					modifier = Modifier
                        .border(2.dp, MaterialTheme.colorScheme.secondary)
                        .padding(12.dp),
					action = {
						TextButton(
							onClick = { if (isError) data.dismiss() else data.performAction() },
							colors = buttonColor
						) { Text(data.visuals.actionLabel ?: "") }
					}
				) {
					Text(data.visuals.message)
				}
			}
		},
	) { innerPadding ->

		Box(
			modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
		) {
			Column(
				modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
			) {

				DeviceIdContainer(device.value!!.id)

				DeviceNameContainer(viewModel = viewModel, connected = device.value!!.connected, deviceNameError = deviceNameError, focusManager = focusManager )

				Text(
					text = stringResource(id = R.string.rssi_header).uppercase(),
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
					style = MaterialTheme.typography.labelSmall,
					modifier = Modifier
						.padding(vertical = 4.dp)
				)
				Text(
					text = "${device.value!!.rssi}",
					color = MaterialTheme.colorScheme.onSurface,
					style = MaterialTheme.typography.bodyLarge,
					modifier = Modifier
						.padding(vertical = 4.dp)
				)

				Text(
					text = stringResource(id = R.string.connection_status).uppercase(),
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
					style = MaterialTheme.typography.labelSmall,
					modifier = Modifier
						.padding(vertical = 4.dp)
				)
				Text(
					text = "${device.value!!.connected}",
					color = MaterialTheme.colorScheme.onSurface,
					style = MaterialTheme.typography.bodyLarge,
					modifier = Modifier
						.padding(vertical = 4.dp)
				)

				Text(
					text = stringResource(id = R.string.last_connected_header).uppercase(),
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
					style = MaterialTheme.typography.labelSmall,
					modifier = Modifier
						.padding(vertical = 4.dp)
				)
				Text(
					text = "${device.value!!.latestConnectedTime}",
					color = MaterialTheme.colorScheme.onSurface,
					style = MaterialTheme.typography.bodyLarge,
					modifier = Modifier
						.padding(vertical = 4.dp)
				)

				Text(
					text = stringResource(id = R.string.elapsed_time_connected_header).uppercase(),
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
					style = MaterialTheme.typography.labelSmall,
					modifier = Modifier
						.padding(vertical = 4.dp)
				)
				Text(
					text = "${device.value!!.elapsedSecsConnected}",
					color = MaterialTheme.colorScheme.onSurface,
					style = MaterialTheme.typography.bodyLarge,
					modifier = Modifier
						.padding(vertical = 4.dp)
				)

				Button(
					onClick = {
						if (viewModel.device.value?.connected == true) {
							viewModel.disconnectDevice()
						} else {
							viewModel.connectDevice()
						}
					},

					modifier = Modifier
						.fillMaxWidth()
				) {
					Text(
						if (viewModel.device.value?.connected == true) {
							stringResource(id = R.string.disconnect_device)
						} else {
							stringResource(id = R.string.connect_device)
						}
					)
				}

			}
		}

	}

	when (viewState) {
		is DetailsScreenState.Loading -> {
			Box(
				contentAlignment = Alignment.Center,
				modifier = Modifier
					.clickable(
						indication = null,
						interactionSource = remember { MutableInteractionSource() } // This is mandatory
					) {
						// action
					}
					.fillMaxSize()
					.background(Color.Black.copy(alpha = 0.6f))
			) {
				CircularProgressIndicator(
					modifier = Modifier.padding(8.dp),
					strokeWidth = 2.dp
				)
			}
		}
		else -> {}
	}
}

@Composable
fun DeviceIdContainer(deviceId: String) {
	Text(
		text = stringResource(id = R.string.device_id).uppercase(),
		color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
		style = MaterialTheme.typography.labelSmall,
		modifier = Modifier
			.padding(vertical = 4.dp)
	)
	Text(
		text = deviceId,
		color = MaterialTheme.colorScheme.onSurface,
		style = MaterialTheme.typography.bodyLarge,
		modifier = Modifier
			.padding(vertical = 4.dp)
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceNameContainer(viewModel: DetailsScreenViewModel, connected: Boolean,  deviceNameError: MutableState<Boolean>, focusManager: FocusManager) {
	TextField(
		value = viewModel.deviceName.value,
		enabled = connected,
		onValueChange = {
			viewModel.deviceName.value = it
			viewModel.verifyDeviceName(it)
//                    isError = false
		},
		singleLine = true,
		label = {
			Text(
				if (deviceNameError.value) {
					stringResource(id = R.string.device_name_invalid)
				} else {
					stringResource(id = R.string.device_name)
				}
			)
		},
		isError = deviceNameError.value,
		keyboardActions = KeyboardActions {
			viewModel.changeDeviceName()
		},
		modifier = Modifier
			.fillMaxWidth()

	)
	if (connected) {
		Text(
			text = stringResource(id = R.string.device_name_error_message),
			color = MaterialTheme.colorScheme.error,
			style = MaterialTheme.typography.bodySmall,
			modifier = Modifier
				.padding(top = 4.dp)
				.alpha(if (deviceNameError.value) 1f else 0f)
		)
	} else {
		focusManager.clearFocus()
		Text(
			text = stringResource(id = R.string.device_name_textfield_connect_helper),
			color = MaterialTheme.colorScheme.onSurface,
			style = MaterialTheme.typography.bodySmall,
			modifier = Modifier
				.padding(top = 4.dp)
		)
	}
}
