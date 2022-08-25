package co.hatch.ui.home

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import co.hatch.R
import co.hatch.deviceClientLib.model.Device
import co.hatch.navigation.LocalMainNavController
import co.hatch.navigation.Screen
import co.hatch.ui.common.SnackbarVisualsWithError
import co.hatch.ui.common.TopBar
import kotlinx.coroutines.delay

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val state by viewModel.uiState.collectAsState()

    HomeScreen(
        viewState = state,
        viewModel = viewModel,
        navController = LocalMainNavController.current
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun HomeScreen(
    viewState: HomeScreenState,
    viewModel: HomeScreenViewModel,
    navController: NavController
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val onDeviceClicked: (Device) -> Unit = { device ->
        viewModel.setDeviceDetailsArgument(device)
        navController.navigate(Screen.Details.route)
    }

    LaunchedEffect(Unit) {
        while (true) {
            viewModel.discoverDevices()
            delay(10000)
        }
    }

    Scaffold(
        topBar = { TopBar(stringResource(id = R.string.app_name), false) },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
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
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .consumedWindowInsets(innerPadding),
            contentPadding = innerPadding
        ) {
            if (viewModel.discoveredDevices.size == 0) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(R.string.no_devices))
                        Button(
                            onClick = { viewModel.discoverDevices() },
                        ) {
                            Text(stringResource(id = R.string.discover_devices))
                        }
                    }
                }
            } else {
                items(viewModel.discoveredDevices.size) { index ->

                    DeviceListItem(
                        device = viewModel.discoveredDevices[index],
                        onDeviceClicked = onDeviceClicked
                    )

                }

                item(key = "bottomSpacer") {
                    Spacer(
                        modifier = Modifier.windowInsetsBottomHeight(
                            WindowInsets.navigationBars
                        )
                    )
                }
            }
        }
    }

    when (viewState) {
        is HomeScreenState.Loading -> {
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
        is HomeScreenState.Error -> {
            viewModel.showSnackBar(snackbarHostState, true)
            viewModel.resetViewState()
        }
        else -> {}
    }
}
