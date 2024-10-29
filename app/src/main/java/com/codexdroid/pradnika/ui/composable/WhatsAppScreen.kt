package com.codexdroid.pradnika.ui.composable

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codexdroid.pradnika.R
import com.codexdroid.pradnika.room.Country
import com.codexdroid.pradnika.room.RoomViewModel
import com.codexdroid.pradnika.room.TableSearchHistory
import com.codexdroid.pradnika.room.ViewModelProvider
import com.codexdroid.pradnika.utils.FontFamilyType
import com.codexdroid.pradnika.utils.getAppFont
import com.codexdroid.pradnika.utils.isWhatsAppInstalled
import com.codexdroid.pradnika.utils.loadJsonFile
import com.codexdroid.pradnika.utils.openWhatsApp

@Preview
@Composable
fun WhatsAppScreenPreview() {
    WhatsAppScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhatsAppScreen(
    modifier: Modifier = Modifier,
    roomViewModel: RoomViewModel = viewModel(factory = ViewModelProvider.factory)
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val country = loadJsonFile(context)
    var selectedCountry by remember { mutableStateOf(country.first()) }
    var mobile by remember { mutableStateOf("") }
    val history by roomViewModel.mobileNos.collectAsState()

    var searchContent by remember { mutableStateOf("") }
    val filteredCountry = country.filter {
        it.name.lowercase().contains(searchContent.lowercase()) ||
                it.code.contains(searchContent.lowercase())
    }

    var openModalBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { true })


    Column (modifier = modifier.fillMaxSize()) {

        Row (
            modifier = modifier.padding(start = 14.dp, top = 10.dp, end = 14.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Column(modifier = modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.str_open),
                    fontFamily = getAppFont(FontFamilyType.REGULAR)
                )
                Text(
                    text = stringResource(R.string.str_whats_app),
                    fontFamily = getAppFont(FontFamilyType.BOLD),
                    fontSize = 20.sp
                )
            }
        }

        Column(modifier = modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)) {

            Row (verticalAlignment = Alignment.CenterVertically) {

                OutlinedButton(onClick = {
                    openModalBottomSheet = true
                }, modifier = modifier.padding(end = 10.dp)) {
                    Text(
                        text = selectedCountry.code,
                        fontFamily = getAppFont(FontFamilyType.REGULAR),
                        modifier = modifier.padding(8.dp)
                    )
                }
                OutlinedTextField(
                    value = mobile,
                    onValueChange = { mobile = it },
                    modifier = modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.None),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    })
                )
            }
            Button(onClick = {
                if (mobile.isEmpty()) {
                    Toast.makeText(context,
                        context.getString(R.string.str_invalid_mobile),
                        Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (isWhatsAppInstalled(context)) {

                    openWhatsApp(context, "${selectedCountry.code}${mobile}")

                    roomViewModel.insert(
                        TableSearchHistory(
                            id = selectedCountry.id,
                            mobile = mobile,
                            countryCode = selectedCountry.code,
                            countryName = selectedCountry.name,
                            timeInMilliSec = System.currentTimeMillis()
                        )
                    )
                    roomViewModel.getSearchHistory()
                }
                else {
                    Toast.makeText(context,
                        context.getString(R.string.str_oops_no_whatsapp_installed),
                        Toast.LENGTH_SHORT).show()
                    return@Button
                }
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = modifier
                    .align(alignment = Alignment.End)
                    .padding(top = 4.dp)
                    .fillMaxWidth()) {
                Text(
                    stringResource(R.string.str_open),
                    fontFamily = getAppFont(FontFamilyType.REGULAR))
            }
        }

        Row (modifier = modifier.padding(start = 10.dp, top = 20.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically){
            Text(
                text = stringResource(R.string.str_history),
                fontFamily = getAppFont(FontFamilyType.BOLD),
                fontSize = 24.sp,
            )

            Spacer(modifier = modifier.weight(1f))

            TextButton(onClick = {
                roomViewModel.remove()
            }) {
                Text(
                    text = stringResource(R.string.str_clear_all),
                    fontFamily = getAppFont(FontFamilyType.REGULAR),
                    fontSize = 14.sp)
            }
        }

        LazyColumn {
            items(history) {
                HistoryItem(it,{
                    roomViewModel.remove(it)
                })
            }
        }


        if (openModalBottomSheet) {

            BottomSheetScaffold(sheetContent = {

                OutlinedTextField(
                    value = searchContent,
                    onValueChange = {searchContent = it},
                    label = {
                        Text(text = stringResource(R.string.str_search_your_country))
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.None),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    })
                )

                LazyColumn {
                    items(filteredCountry) {
                        CountryItem(it,{selected ->
                            selectedCountry = selected
                            keyboardController?.hide()
                            openModalBottomSheet = false

                        })
                    }

                }

            },modifier = modifier,
                scaffoldState = rememberBottomSheetScaffoldState(),
                sheetPeekHeight = 800.dp,
                sheetMaxWidth = BottomSheetDefaults.SheetMaxWidth,
                sheetContainerColor = Color(0xFFEEEEEE),
                sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                sheetShadowElevation = 10.dp,
                sheetTonalElevation = 20.dp,
                sheetDragHandle = {  BottomSheetDefaults.DragHandle() },
                sheetSwipeEnabled = false,
                topBar = {
                    TopAppBar(title = { Text(text = "Top App Bar")} )
                }
            ) {}
        }
    }
}


@Preview
@Composable
fun HistoryItemPreview(modifier: Modifier = Modifier) {
    HistoryItem(TableSearchHistory(0,"9657513437","+91","India",1730175260958), {})
}

@Composable
fun HistoryItem(
    history: TableSearchHistory,
    onSingleEntryRemove:(TableSearchHistory) -> Unit,
    modifier: Modifier = Modifier) {

    val context = LocalContext.current
    Column {
        Row (
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {

            TextButton(onClick = {
                openWhatsApp(context, "${history.countryCode} ${history.mobile}")
            }) {
                Text(text = "${history.countryCode} ${history.mobile}",
                    fontFamily = getAppFont(FontFamilyType.REGULAR),
                    fontWeight = FontWeight.W800
                )
            }

            Spacer(modifier = modifier.weight(1f))
            Text(
                text = history.getTime(), //"10 July 2024, 05:12 PM",
                fontFamily = getAppFont(FontFamilyType.REGULAR),
                fontSize = 14.sp
            )

            IconButton(onClick = {
                onSingleEntryRemove(history)
            }) {
                Icon(painter = painterResource(R.drawable.ic_close),
                    contentDescription = stringResource(R.string.str_remove_history),
                    modifier = modifier.size(30.dp)
                )
            }
        }
        HorizontalDivider(modifier = modifier.padding(horizontal = 10.dp))
    }
}


@Preview
@Composable
fun CountryItemPreview(modifier: Modifier = Modifier) {
    CountryItem(Country(76,"India","+91"),{})
}
@Composable
fun CountryItem(country: Country,
                onSelect:(Country) -> Unit,
                modifier: Modifier = Modifier) {

    Column {

        Row (modifier = modifier
            .fillMaxWidth()
            .padding(14.dp)
            .clickable { onSelect(country) }){

            Text(
                text = country.code,
                fontFamily = getAppFont(FontFamilyType.REGULAR),
                fontSize = 20.sp
            )
            Spacer(modifier = modifier.padding(horizontal = 10.dp))
            Text(
                text = country.name,
                fontFamily = getAppFont(FontFamilyType.REGULAR),
                fontSize = 20.sp
            )
        }

        HorizontalDivider(modifier = modifier.padding(horizontal = 10.dp))
    }

}