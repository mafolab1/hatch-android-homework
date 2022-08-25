package co.hatch.ui.details.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.hatch.R
import co.hatch.ui.details.DetailsScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DeviceNameContainer(
    viewModel: DetailsScreenViewModel,
    connected: Boolean,
    deviceNameError: MutableState<Boolean>,
    focusManager: FocusManager
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

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
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusEvent { focusState ->
                if (focusState.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            }

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