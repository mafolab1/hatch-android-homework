package co.hatch.ui.home

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import co.hatch.R
import co.hatch.deviceClientLib.model.Device
import co.hatch.navigation.LocalMainNavController
import co.hatch.navigation.Root
import co.hatch.navigation.Screen
import co.hatch.ui.common.TopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    LaunchedEffect(Unit) {
        while(true) {
            viewModel.discoverDevices()
            delay(10000)
        }
    }

    when (viewState) {
        is HomeScreenState.Loading -> {

        }
        else -> {
            Log.e("<><>", "daslkjlaksdjflkasdf")
        }
    }

    val onDeviceClicked: (Device) -> Unit = { device ->
        viewModel.setDeviceDetailsArgument(device)
        navController.navigate(Screen.Details.route)
    }

    Scaffold(
        topBar = { TopBar(stringResource(id = R.string.app_name), false) },
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
        floatingActionButton = {
            var clickCount by remember { mutableStateOf(0) }
            ExtendedFloatingActionButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                           message = "Snackbar # ${++clickCount}",
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            ) { Text("Show snackbar") }
        },
    ) { innerPadding ->

        Column(modifier = Modifier.fillMaxSize()
            .consumedWindowInsets(innerPadding)
        ) {

            LazyColumn(

                modifier = Modifier
                    .fillMaxWidth()
                    .consumedWindowInsets(innerPadding),
                contentPadding = innerPadding
            ) {
                items( viewModel.discoveredDevices.size) { index ->

                    DeviceListItem(device = viewModel.discoveredDevices[index], onDeviceClicked = onDeviceClicked)

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
}
