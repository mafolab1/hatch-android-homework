package co.hatch.ui.details

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import co.hatch.R
import co.hatch.navigation.LocalMainNavController
import co.hatch.ui.common.TopBar

@Composable
fun DetailsScreen() {
    val viewModel = hiltViewModel<DetailsScreenViewModel>()
    val state by viewModel.uiState.collectAsState()

    DetailsScreen(
        viewState = state,
        viewModel = viewModel,
        navController = LocalMainNavController.current
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun DetailsScreen(
    viewState: DetailsScreenState,
    viewModel: DetailsScreenViewModel,
    navController: NavController
) {

    val device = viewModel.getDeviceDetailsArgument()

    Log.e("<><>", "device: $device" )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val deviceNameError = remember { (viewModel.deviceNameTextFieldErrorState) }

    when (viewState) {
        is DetailsScreenState.Loading -> {

        }
        else -> {}
    }


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

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .consumedWindowInsets(innerPadding)
        ) {
            TextField(
                value = viewModel.deviceName.value,
                onValueChange = {
                    viewModel.deviceName.value = it
                    deviceNameError.value = !deviceNameError.value
//                    isError = false
                },
                singleLine = true,
                label = { Text(if (deviceNameError.value) {
                    stringResource(id = R.string.device_name_invalid)
                } else {
                    stringResource(id = R.string.device_name)
                }) },
                isError = deviceNameError.value,
//                keyboardActions = KeyboardActions { validate(text) },
                modifier = Modifier
                    .fillMaxWidth()

            )
            // Supporting text for error message.
            Text(
                text = stringResource(id = R.string.device_name_error_message),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp)
                    .alpha(if (deviceNameError.value) 1f else 0f)
            )

            Text(
                text = stringResource(id = R.string.device_name_error_message),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp)
                    .alpha(if (deviceNameError.value) 1f else 0f)
            )

            Text(
                text = stringResource(id = R.string.device_name_error_message),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp)
                    .alpha(if (deviceNameError.value) 1f else 0f)
            )

            Text(
                text = stringResource(id = R.string.device_name_error_message),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp)
                    .alpha(if (deviceNameError.value) 1f else 0f)
            )

        }
    }

}