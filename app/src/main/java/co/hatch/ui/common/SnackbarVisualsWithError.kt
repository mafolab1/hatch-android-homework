package co.hatch.ui.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

class SnackbarVisualsWithError(
    override val message: String,
    val isError: Boolean
) : SnackbarVisuals {
    override val actionLabel: String
        get() = if (isError) {
            "Error"
        } else {
            "OK"
        }
    override val withDismissAction: Boolean
        get() = false
    override val duration: SnackbarDuration
        get() = if (isError) {
            SnackbarDuration.Long
        } else {
            SnackbarDuration.Short
        }
}