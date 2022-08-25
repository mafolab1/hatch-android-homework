package co.hatch.ui.common

import java.text.SimpleDateFormat
import java.util.*

fun formatLastConnectedTime(date: Date?): String {
    val formatter = SimpleDateFormat("EEE, MMM d, yyyy 'at' HH:mm:ss z", Locale.getDefault())
    return if (date != null) {
        formatter.format(date)
    } else {
        "N/A"
    }
}