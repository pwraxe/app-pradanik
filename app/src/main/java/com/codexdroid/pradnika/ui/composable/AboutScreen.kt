package com.codexdroid.pradnika.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codexdroid.pradnika.R
import com.codexdroid.pradnika.di.data.Social
import com.codexdroid.pradnika.di.data.about
import com.codexdroid.pradnika.di.data.socials
import com.codexdroid.pradnika.utils.FontFamilyType
import com.codexdroid.pradnika.utils.getAppFont

@Preview
@Composable
fun AboutScreenPreview(modifier: Modifier = Modifier) {
    AboutScreen()
}
@Composable
fun AboutScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    Column (modifier = modifier.fillMaxSize()) {

        Spacer(modifier = modifier.padding(top = 30.dp))

        Text(
            text = context.getString(R.string.str_about),//stringResource(R.string.str_about),
            fontSize = 16.sp,
            fontFamily = getAppFont(FontFamilyType.REGULAR),
            modifier = modifier.align(alignment = Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(R.string.app_name),
            fontSize = 26.sp,
            fontFamily = getAppFont(FontFamilyType.BOLD),
            modifier = modifier.align(alignment = Alignment.CenterHorizontally)
        )

        Spacer(modifier = modifier.padding(bottom = 20.dp))

        LazyColumn {
            items(about) {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    fontFamily = getAppFont(FontFamilyType.REGULAR),
                    modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }

        Text(
            text = "Connect With Developer",
            fontSize = 16.sp,
            fontFamily = getAppFont(FontFamilyType.BOLD),
            modifier = modifier.padding(start = 10.dp, top = 20.dp)
        )


        LazyRow {
            items(socials) {
                SocialItems(it,{
                    uriHandler.openUri(it.uri)
                })
            }
        }
    }
}


@Composable
fun SocialItems(social: Social, onClick:(Social) -> Unit,
                modifier: Modifier = Modifier) {
    OutlinedCard (
        onClick = {
            onClick(social)
        },
        modifier = modifier
            .padding(horizontal = 4.dp)
            .size(width = 100.dp, height = 100.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Image(
                painter = painterResource(social.icon),
                contentDescription = stringResource(social.name),
                modifier = modifier
                    .size(40.dp)
                    .padding(6.dp)
            )

            Text(
                text = stringResource(social.name),
                textAlign = TextAlign.Center,
                fontFamily = getAppFont(FontFamilyType.REGULAR),
                modifier = modifier.padding(6.dp)
            )
        }
    }
}