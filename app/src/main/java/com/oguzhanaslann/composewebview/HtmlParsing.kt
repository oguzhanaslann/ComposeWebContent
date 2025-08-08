package com.oguzhanaslann.composewebview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oguzhanaslann.composewebview.ui.theme.ComposeWebViewTheme

@Composable
fun HtmlParsing() {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        /**
         *  added in implementation(platform("androidx.compose:compose-bom:2025.04.01"))
         *
         * ⚠️ Note: Android’s Html.fromHtml() (which backs AnnotatedString.fromHtml) does not support all tags — for example:
         *  under the hood it uses "HtmlCompat.fromHtml(stringToParse, HtmlCompat.FROM_HTML_MODE_COMPACT, null, TagHandler)"
         *  so it inherits every limitations of html compat so be sure to check https://developer.android.com/reference/androidx/core/text/HtmlCompat.
         *
         * for example:
         * <table> and <img> are often not fully supported.
         */
        Text(
            AnnotatedString.fromHtml(
                """
        <h1>Welcome to HTML Content</h1>
        <p>This is a <b>bold</b> and <i>italic</i> paragraph to demonstrate basic styling.</p>

        <h2>Here is a list:</h2>
        <ul>
          <li>Item One</li>
          <li>Item Two with <a href='https://example.com'>a link</a></li>
          <li>Item Three with <b>bold</b> and <i>italic</i> text</li>
        </ul>

        <h2>Ordered Steps:</h2>
        <ol>
          <li>Open the app</li>
          <li>Navigate to the dashboard</li>
          <li>Enjoy the features</li>
        </ol>

        <h2>Blockquote Example:</h2>
        <blockquote>“Code is like humor. When you have to explain it, it’s bad.”</blockquote>

        <h2>Image Example:</h2>
        <p><img src='https://via.placeholder.com/150' alt='Sample Image'></p>

        <h2>Table Example:</h2>
        <table border="1">
          <tr>
            <th>Language</th>
            <th>Popularity</th>
          </tr>
          <tr>
            <td>Kotlin</td>
            <td>High</td>
          </tr>
          <tr>
            <td>Java</td>
            <td>Moderate</td>
          </tr>
        </table>

        <h2>Final Thoughts</h2>
        <p>HTML allows for <u>rich text</u> formatting and is widely supported in many components, though support may vary.</p>
        """.trimIndent()
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun previewHtmlParsing() {
    ComposeWebViewTheme {
        HtmlParsing()
    }
}