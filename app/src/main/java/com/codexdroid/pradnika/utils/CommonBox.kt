package com.codexdroid.pradnika.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.codexdroid.pradnika.R
import com.codexdroid.pradnika.di.Modules
import com.codexdroid.pradnika.room.Country
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale


/**
 * load json
 *
 * add data store in app
 * add room database
 * save data in room database
 * load data from room
 *
 *
 */


enum class FontFamilyType {
    LITE, REGULAR, BOLD
}

enum class NavigateTo {
    HOME, WHATS_APP, QR_CODE, ABOUT
}

fun toJson(data: Any): String = Modules.getGson().toJson(data)
fun getAppFont(type: FontFamilyType): FontFamily {
    val family = when (type) {
        FontFamilyType.LITE -> R.font.font_montserrat_alt_light
        FontFamilyType.REGULAR-> R.font.font_montserrat_alt_regular
        FontFamilyType.BOLD -> R.font.font_montserrat_alt_black
    }
    return FontFamily(Font(family))
}

fun loadJsonFile(context: Context): List<Country> {
    val gson = Modules.getGson()
    val inputStream = context.assets.open("country.json")
    val reader = BufferedReader(InputStreamReader(inputStream))
    val itemType = object : TypeToken<List<Country>>() {}.type
    return gson.fromJson(reader, itemType)
}


fun requestTime(time: Long?) : String {
    //07 Sep 2023, 07:09 AM
    val simpleDateFormat = SimpleDateFormat("dd MMM yyyy hh:mm aa", Locale.getDefault())
    return simpleDateFormat.format(time)
}

fun isWhatsAppInstalled(context: Context) : Boolean {
    return try {
        context.packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun openWhatsApp(context: Context, mobile: String) {


    val url = "https://api.whatsapp.com/send?phone=$mobile"
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    context.startActivity(intent)
}
