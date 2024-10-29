package com.codexdroid.pradnika.di.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.codexdroid.pradnika.R
import com.codexdroid.pradnika.utils.NavigateTo

data class HomeItem(
    val id:NavigateTo = NavigateTo.HOME,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
)

val homeItems = listOf(
    HomeItem(
        id = NavigateTo.WHATS_APP,
        name = R.string.str_open_whats_app,
        icon = R.drawable.ic_whatsapp_black),

    HomeItem(
        id = NavigateTo.QR_CODE,
        name = R.string.str_scan_codes,
        icon = R.drawable.ic_qr_code),

//    HomeItem(
//        id = NavigateTo.BAR_CODE,
//        name = R.string.str_scan_bar_code,
//        icon = R.drawable.ic_barcode),
)


val about = listOf(
    "Pradānika (प्रदानिक) is Sanscrit word taken from Google Gemini, It is likely refers to something related to giving, offering, or bestowing.",
    "The intention behind the app is simply to open WhatsApp without saving number in the phone, also few old devices don’t have QR scanning is built-in, and it also works for them.",
    "Developer having more than 3 years of experience in Native Android Kotlin located in Pune working as a Software Developer.",
    "The app is specifically designed in only for Android natives in Jetpack compose, the App does not  collect any data from the user, based on the user uses the app to save data in his cache memory (So relax...).",
    "Monetization will be removed soon if it doesn’t work for the developer.",
    "Thanks for having this application..."
)

data class Social(
    @DrawableRes
    val icon: Int,
    @StringRes
    val name: Int,
    val uri: String
)
val socials = listOf(
    Social(
        icon = R.drawable.ic_linkedin,
        name = R.string.str_connect,
        uri = "https://www.linkedin.com/in/akshay-pawar-b7a0a8135/"
    ),
    Social(
        icon = R.drawable.ic_github,
        name =R.string.str_follow,
        uri = "https://github.com/pwraxe"
    ),
    Social(
        icon = R.drawable.ic_browser,
        name = R.string.str_visit,
        uri = "https://pwraxe.github.io/"
    ),
)