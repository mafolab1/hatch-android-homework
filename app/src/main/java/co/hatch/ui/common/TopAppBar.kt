package co.hatch.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.hatch.navigation.LocalMainNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, showNavIcon: Boolean) {
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
			if(showNavIcon) {
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
