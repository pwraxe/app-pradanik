package com.codexdroid.pradnika.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codexdroid.pradnika.R
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreenPreview(modifier: Modifier = Modifier) {
    SplashScreen(modifier) {}
}

@Composable
fun SplashScreen(modifier: Modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),  onTimeOut:() -> Unit) {

    LaunchedEffect(Unit) {
        delay(2000)
        onTimeOut()
    }
    Box (modifier = modifier
        .fillMaxSize()
        .background(color = Color.White),
        contentAlignment = Alignment.Center) {

        Image(
            painter = painterResource(R.drawable.ic_logo_foreground),
            contentDescription = "app logo",
            modifier = modifier.size(600.dp))
    }
}
