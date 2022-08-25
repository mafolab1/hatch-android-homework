package co.hatch.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import co.hatch.navigation.LocalMainNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, showNavIcon: Boolean, clearNavArugments) {
	val navController = LocalMainNavController.current
	SmallTopAppBar(
		title = {
			Text(
				text = title,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
				style = TextStyle(
					fontFamily = FontFamily.Default,
					fontWeight = FontWeight.Medium,
				),
				fontSize = 20.sp,
				color = MaterialTheme.colorScheme.onPrimary,
			)
		},
		navigationIcon = {
			if (showNavIcon) {
				IconButton(
					onClick = {
						navController.popBackStack()
					}
				) {
					Icon(
						Icons.Filled.ArrowBack, "Back",
						tint = MaterialTheme.colorScheme.onPrimary,
					)
				}
			}
		},
		colors = TopAppBarDefaults.mediumTopAppBarColors(
			containerColor = MaterialTheme.colorScheme.primary
		),
	)
}
