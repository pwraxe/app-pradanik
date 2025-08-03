package com.codexdroid.pradnika.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codexdroid.pradnika.R
import com.codexdroid.pradnika.utils.NavigateTo
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
    modifier: Modifier = Modifier ,
    navHostController: NavHostController = rememberNavController()) {

    Scaffold (
        topBar = {},
        modifier = modifier.fillMaxSize()){
        val pv = it
        Column(modifier = modifier) {

            AndroidView(modifier = modifier.fillMaxWidth().wrapContentHeight(), factory = {context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = context.getString(R.string.app_unit_id)
                    loadAd(AdRequest.Builder().build())
                    this.adListener = object : AdListener(){}
                }
            })

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