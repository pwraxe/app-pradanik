package com.codexdroid.pradnika.ui.composable

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.codexdroid.pradnika.R
import com.codexdroid.pradnika.utils.FontFamilyType
import com.codexdroid.pradnika.utils.getAppFont
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.contracts.contract

@Preview
@Composable
fun QRCodeScreenPreview() {
    QRCodeScreen()
}

@Composable
fun QRCodeScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()
    var qrCodeData by remember { mutableStateOf("") }
    var isCopied by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(contract = ScanContract()) {result ->
        result?.let {
            it.contents?.let {
                qrCodeData = result.contents
            }

        }
    }
    Column(modifier = modifier
        .fillMaxSize()
        .padding(10.dp)) {

        Text(
            text = stringResource(R.string.str_scan),
            fontFamily = getAppFont(FontFamilyType.REGULAR)
        )
        Text(
            text = stringResource(R.string.str_qr_code_barcode_many_more),
            fontFamily = getAppFont(FontFamilyType.BOLD),
            fontSize = 20.sp
        )

        Button(
            onClick = {
                val option = ScanOptions().apply {
                    setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
                    setPrompt("Scan QR Code")
                    setCameraId(0)
                    setBeepEnabled(true)
                    setBarcodeImageEnabled(true)
                    setOrientationLocked(true)
                }
                launcher.launch(option)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            modifier = modifier
                .align(
                    alignment = Alignment.CenterHorizontally
                )
                .padding(top = 30.dp)
        ) {
            Text(
                text = stringResource(R.string.str_scan_qr_code),
                fontFamily = getAppFont(FontFamilyType.REGULAR),
            )
        }

        Spacer(modifier = modifier.padding(top = 10.dp))

        if (qrCodeData.isNotEmpty()) {
            Row(horizontalArrangement = Arrangement.End, modifier = modifier.align(alignment = Alignment.End)) {

                IconButton(onClick = {
                    clipboardManager.setText(AnnotatedString(qrCodeData))
                    Toast.makeText(context, context.getString(R.string.str_copied), Toast.LENGTH_SHORT).show()
                    isCopied = true
                    coroutineScope.launch {
                        delay(2000)
                        isCopied = false
                    }
                }
                ) {

                    Icon(
                        painter = painterResource(if(isCopied) R.drawable.ic_copy_after else R.drawable.ic_copy_be4),
                        contentDescription = stringResource(R.string.str_copy_scanned_content_button),
                        modifier = modifier.size(20.dp)
                    )
                }

                IconButton(onClick = {
                    Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, qrCodeData)

                        ContextCompat.startActivities(
                            context,
                            arrayOf(
                                Intent.createChooser(
                                    this, context.getString(R.string.str_share_data_with), null
                                )
                            )
                        )
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = stringResource(R.string.str_copy_scanned_content_button),
                        modifier = modifier.size(20.dp)
                    )
                }
            }
            Text(
                text = qrCodeData,
                fontSize = 14.sp,
                fontFamily = getAppFont(FontFamilyType.REGULAR)
            )
        }
    }
}