package co.hatch.ui.details

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import co.hatch.R
import co.hatch.navigation.LocalMainNavController
import co.hatch.ui.common.TopBar
import co.hatch.ui.home.DeviceListItem
import kotlinx.coroutines.launch

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

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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

        Column(modifier = Modifier.fillMaxSize()
            .consumedWindowInsets(innerPadding)
        ) {


        }
    }

}