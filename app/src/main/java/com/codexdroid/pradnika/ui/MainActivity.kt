package com.codexdroid.pradnika.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.codexdroid.pradnika.ui.composable.Initializer
import com.codexdroid.pradnika.ui.composable.SplashScreen
import com.codexdroid.pradnika.ui.theme.PradānikaTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MobileAds.initialize(this)
            PradānikaTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(color = Color.White)
                LetsStart( modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun LetsStart (modifier: Modifier = Modifier) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Initializer()
    }
    else {
        var isSplashVisible by remember { mutableStateOf(true) }
        if (isSplashVisible) {
            SplashScreen(modifier) {
                isSplashVisible = false
            }
        }
        else {
            Initializer()
        }
    }
}