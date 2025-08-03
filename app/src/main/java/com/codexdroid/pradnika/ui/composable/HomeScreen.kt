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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onItemClicked: (HomeItem) -> Unit,
    onFabClicked: () -> Unit,
    modifier: Modifier = Modifier) {

    Scaffold(
        topBar = {},
    ) {innerPadding ->
        Box(modifier = modifier .fillMaxSize()
            .padding(innerPadding)
            .background(color = Color.White)){

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

            FloatingActionButton(onClick = { onFabClicked() },
                modifier = modifier.align(alignment = Alignment.BottomEnd).padding(16.dp)) {
                Row (verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(horizontal = 16.dp)){
                    Icon(imageVector = Icons.Default.Info,
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
                modifier = modifier.padding(6.dp),
                color = Color.Black
            )
        }
    }
}
 