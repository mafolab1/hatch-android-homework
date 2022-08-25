package co.hatch.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import co.hatch.R
import co.hatch.ui.common.SnackbarVisualsWithError
import co.hatch.ui.common.TopBar
import co.hatch.ui.details.components.*

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

    val goBack: () -> Unit = {
        viewModel.clearNavArgs()
    }

    val connectionButtonPressed: () -> Unit = {
        if (viewModel.device.value?.connected == true) {
            viewModel.disconnectDevice()
        } else {
            viewModel.connectDevice()
        }
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = { TopBar(stringResource(id = R.string.device_details), true, goBack) },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                val isError = (data.visuals as? SnackbarVisualsWithError)?.isError ?: false

                Snackbar(
                    modifier = Modifier
                        .padding(12.dp),
                    action = {
                    },
                    containerColor = if (isError) MaterialTheme.colorScheme.error else SnackbarDefaults.color
                ) {
                    Text(
                        text = data.visuals.message,
                        color = if (isError) MaterialTheme.colorScheme.onError else SnackbarDefaults.contentColor
                    )
                }
            }
        },
    ) { innerPadding ->

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
        ) {
            val (itemList, connectionButton) = createRefs()

            //Created a lazy column for smaller device so the user will be able to scroll the content
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(itemList) {
                        top.linkTo(parent.top)
                    }
            ) {


                //Broke up the items just to make the code clearer and easier to read.
                item {
                    DeviceIdContainer(device.value!!.id)
                }

                item {
                    DeviceNameContainer(
                        viewModel = viewModel,
                        connected = device.value!!.connected,
                        deviceNameError = deviceNameError,
                        focusManager = focusManager
                    )
                }

                item {
                    RssiComponent(device.value!!.rssi)

                }

                item {
                    ConnectedComponent(device.value!!.connected)

                }

                item {
                    LastConnectedComponent(device.value!!.latestConnectedTime)

                }

                item {
                    ElapsedTimeComponent(device.value!!.elapsedSecsConnected)

                }
            }

            ConnectionButtonComponent(device.value!!.connected,
                connectionButtonPressed,
                modifier = Modifier
                    .padding(16.dp)
                    .constrainAs(connectionButton) {
                        top.linkTo(itemList.bottom)
                        baseline.linkTo(parent.baseline)
                        bottom.linkTo(parent.bottom)
                    })
        }


    }

    when (viewState) {
        is DetailsScreenState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {}
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(8.dp),
                    strokeWidth = 2.dp
                )
            }
        }
        is DetailsScreenState.Success -> {
            focusManager.clearFocus()
            viewModel.showSnackBar(snackbarHostState, false)
            viewModel.resetViewState()
        }
        is DetailsScreenState.Error -> {
            viewModel.showSnackBar(snackbarHostState, true, viewState.errorMessage)
            viewModel.resetViewState()
        }
        else -> {}
    }
}






