package co.hatch

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import co.hatch.navigation.LocalMainNavController
import co.hatch.navigation.Root
import co.hatch.ui.home.HomeScreen
import co.hatch.ui.theme.HatchHomeworkProject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("<><>", "asdfasdfa")
        renderUi()
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    private fun renderUi() = setContent {

        val systemTheme = isSystemInDarkTheme()
        val isDarkTheme = remember { mutableStateOf(systemTheme) }
        val mainMavController = rememberNavController()

        HatchHomeworkProject {
            CompositionLocalProvider(
                LocalMainNavController provides mainMavController,
            ) {

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Root(mainMavController)
                }
            }
        }
    }
}