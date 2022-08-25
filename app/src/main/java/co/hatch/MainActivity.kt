package co.hatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import co.hatch.deviceClientLib.connectivity.ConnectivityClient
import co.hatch.navigation.LocalMainNavController
import co.hatch.navigation.Root
import co.hatch.theme.HatchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        renderUi()
    }

    private fun renderUi() = setContent {

        val systemTheme = isSystemInDarkTheme()
        val isDarkTheme = remember { mutableStateOf(systemTheme) }
        val mainMavController = rememberNavController()

        HatchTheme {
            CompositionLocalProvider(
                LocalMainNavController provides mainMavController,
                //LocalBackPressedDispatcher provides onBackPressedDispatcher
            ) {

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Root(isDarkTheme)
                }

            }
        }
    }
}