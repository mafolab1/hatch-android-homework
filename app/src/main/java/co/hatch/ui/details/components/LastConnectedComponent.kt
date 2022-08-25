package co.hatch.ui.details.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.hatch.R
import co.hatch.ui.common.formatLastConnectedTime
import java.util.*

@Composable
fun LastConnectedComponent(latestConnectedTime: Date?) {
    Text(
        text = stringResource(id = R.string.last_connected_header).uppercase(),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .padding(vertical = 4.dp)
    )
    Text(
        text = stringResource(
            id = R.string.last_connected,
            formatLastConnectedTime(latestConnectedTime),
        ),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .padding(vertical = 4.dp)
    )
}