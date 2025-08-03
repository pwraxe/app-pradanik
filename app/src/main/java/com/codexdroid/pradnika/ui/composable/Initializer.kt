package com.codexdroid.pradnika.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codexdroid.pradnika.R
import com.codexdroid.pradnika.ui.theme.Purple40
import com.codexdroid.pradnika.ui.theme.Purple80
import com.codexdroid.pradnika.utils.FontFamilyType
import com.codexdroid.pradnika.utils.NavigateTo
import com.codexdroid.pradnika.utils.getAppFont
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView


@Preview
@Composable
fun InitPreview(modifier: Modifier = Modifier) {
    Initializer()
}

@Composable
fun Initializer(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()) {

    var isAdShown by remember { mutableStateOf(false) }

    Surface (modifier = modifier.fillMaxSize().background(color = Purple80)){
        Column(modifier = modifier.background(color = Color.Gray)) {

            Spacer(modifier = modifier.padding(top = 10.dp))

            AndroidView(modifier = modifier.fillMaxWidth(), factory = {context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = context.getString(R.string.app_unit_id)
                    loadAd(AdRequest.Builder().build())
                    this.adListener = object : AdListener(){}
                }
            })

            HorizontalDivider(modifier = modifier.padding(vertical = 10.dp))

            NavHost(
                navController = navHostController,
                startDestination = NavigateTo.HOME.name) {

                composable(NavigateTo.HOME.name) {
                    HomeScreen(onItemClicked = {item ->
                        val route = if (item.id == NavigateTo.WHATS_APP) NavigateTo.WHATS_APP else NavigateTo.QR_CODE
                        navHostController.navigate(route.name)
                    }, onFabClicked = {
                        navHostController.navigate(NavigateTo.ABOUT.name)
                    },modifier)
                }
                composable(NavigateTo.WHATS_APP.name) {
                    WhatsAppScreen()
                }
                composable(NavigateTo.QR_CODE.name) {
                    QRCodeScreen()
                }
                composable(NavigateTo.ABOUT.name) {
                    AboutScreen()
                }
            }
        }
    }
}