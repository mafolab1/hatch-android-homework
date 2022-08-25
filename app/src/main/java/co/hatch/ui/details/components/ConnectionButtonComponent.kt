package co.hatch.ui.details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import co.hatch.R

@Composable
fun ConnectionButtonComponent(
    connected: Boolean,
    pressedAction: () -> Unit = {},
    modifier: Modifier
) {
    Button(
        onClick = { pressedAction() },
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            if (connected) {
                stringResource(id = R.string.disconnect_device)
            } else {
                stringResource(id = R.string.connect_device)
            }
        )
    }

}