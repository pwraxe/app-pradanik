package com.codexdroid.pradnika.ui.composable

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.codexdroid.pradnika.utils.FontFamilyType
import com.codexdroid.pradnika.utils.getAppFont

@Preview
@Composable
fun DynamicLinkPreview(modifier: Modifier = Modifier) {
    Column {
        DynamicLinkText("Contact us at info@example.com or call 123-456-7890. Visit https://www.example.com for more info")
    }
}


@Composable
fun DynamicLinkText(inputText: String) {
    val context = LocalContext.current

    // Regular expressions to find email, phone numbers, and URLs
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    val phoneRegex = Regex("\\b\\d{10}\\b|\\b\\d{3}[-\\.\\s]?\\d{3}[-\\.\\s]?\\d{4}\\b")
    val urlRegex = Regex("https?://[\\w\\.-]+(:\\d+)?(/[\\w\\-./?%&=]*)?")

    // Build the annotated string
    val annotatedString = remember(inputText) {

        buildAnnotatedString {
            var lastIndex = 0

            // Find and annotate emails
            emailRegex.findAll(inputText).forEach { matchResult ->
                append(inputText.substring(lastIndex, matchResult.range.first))
                val email = matchResult.value
                pushStringAnnotation(tag = "email", annotation = email)
                withStyle(
                    style = SpanStyle(textDecoration = TextDecoration.Underline,
                    color = Color.Blue)) {
                    append(email)
                }
                pop()
                lastIndex = matchResult.range.last + 1
            }

            // Find and annotate phone numbers
            phoneRegex.findAll(inputText).forEach { matchResult ->
                try {
                    append(inputText.substring(lastIndex, matchResult.range.first))
                    Log.d("AXE","SubString-1: ${inputText.substring(lastIndex, matchResult.range.first)}")
                }catch (ex: Exception) {
                    append(inputText.substring(matchResult.range.first, lastIndex))
                    Log.d("AXE","SubString-2: ${inputText.substring(matchResult.range.first, lastIndex)}")
                }

                val phone = matchResult.value
                pushStringAnnotation(tag = "phone", annotation = "tel:$phone")
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color.Blue)
                ) {
                    append(phone)
                }
                pop()
                lastIndex = matchResult.range.last + 1
            }

            // Find and annotate URLs
            urlRegex.findAll(inputText).forEach { matchResult ->
                append(inputText.substring(lastIndex, matchResult.range.first))
                val url = matchResult.value
                pushStringAnnotation(tag = "url", annotation = url)
                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color.Blue)
                ) {
                    append(url)
                }
                pop()
                lastIndex = matchResult.range.last + 1
            }

            append(inputText.substring(lastIndex))
            withStyle(style = SpanStyle(fontSize = 14.sp, fontFamily = getAppFont(FontFamilyType.REGULAR))) {
                toAnnotatedString()
            }
        }
    }

    // Handle clicks on the annotated text
    ClickableText(
        text = annotatedString,
        style = TextStyle(fontFamily = getAppFont(FontFamilyType.REGULAR), fontSize = 14.sp),
        onClick = { offset ->
            annotatedString.getStringAnnotations(start = offset, end = offset).firstOrNull()?.let { annotation ->
                when (annotation.tag) {
                    "email" -> {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:${annotation.item}") // Only email apps should handle this
                        }
                        context.startActivity(intent)
                    }
                    "phone" -> {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse(annotation.item)
                        }
                        context.startActivity(intent)
                    }
                    "url" -> {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(annotation.item)
                        }
                        context.startActivity(intent)
                    }
                }
            }
        }
    )
}
