package com.codexdroid.pradnika.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codexdroid.pradnika.R
import com.codexdroid.pradnika.di.data.HomeItem
import com.codexdroid.pradnika.di.data.homeItems
import com.codexdroid.pradnika.utils.FontFamilyType
import com.codexdroid.pradnika.utils.NavigateTo
import com.codexdroid.pradnika.utils.getAppFont

@Preview
@Composable
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    HomeScreen({},{},modifier)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    onItemClicked: (HomeItem) -> Unit,
    onFabClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()){

        FlowRow (modifier = modifier.padding(top = 6.dp)) {
            homeItems.forEach {
                HomeScreenItem(
                    homeItem = it,
                    onItemClicked = {item ->
                        onItemClicked(item)
                    }
                )
            }
        }

        Row (modifier = modifier
            .padding(10.dp)
            .align(alignment = Alignment.BottomEnd)) {

            ExtendedFloatingActionButton(onClick = { onFabClicked() }) {
                Row (verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Default.Face,
                        contentDescription = stringResource(R.string.str_developer_info))
                    Spacer(modifier.padding(start = 6.dp))
                    Text(stringResource(R.string.str_app_info))
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenItemPreview(modifier: Modifier = Modifier) {
    HomeScreenItem(
        HomeItem(
            id = NavigateTo.HOME,
            name = R.string.app_name,
            icon = R.drawable.ic_launcher_background
        ),
        onItemClicked = {}
    )
}
@Composable
fun HomeScreenItem(
    homeItem: HomeItem,
    onItemClicked:(HomeItem)-> Unit,
    modifier: Modifier = Modifier) {

    OutlinedCard (
        onClick = { onItemClicked(homeItem) },
        modifier = modifier
            .padding(horizontal = 4.dp)
            .size(width = 100.dp, height = 100.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Image(
                painter = painterResource(homeItem.icon),
                contentDescription = stringResource(homeItem.name),
                modifier = modifier
                    .size(40.dp)
                    .padding(6.dp)
            )

            Text(
                text = stringResource(homeItem.name),
                textAlign = TextAlign.Center,
                fontFamily = getAppFont(FontFamilyType.REGULAR),
                modifier = modifier.padding(6.dp)
            )
        }
    }
}
 