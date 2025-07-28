package com.oguzhanaslann.composewebview

import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.ACTIVITY_HEIGHT_FIXED
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.oguzhanaslann.composewebview.ui.theme.ComposeWebViewTheme

@Composable
fun CustomTabsView() {
    val context = LocalContext.current
    val url = "https://www.google.com"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                val customTabsIntent = CustomTabsIntent.Builder().build()
                customTabsIntent.launchUrl(context, url.toUri())
            }
        ) {
            Text("Basic Custom Tabs")
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                val customTabsIntent = CustomTabsIntent
                    .Builder()
                    .setShowTitle(true)
                    .setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
                    .setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .build()
                customTabsIntent.launchUrl(context, url.toUri())
            }
        ) {
            Text("Customized Custom Tabs")
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                val customTabsIntent = CustomTabsIntent.Builder()
                    .setInitialActivityHeightPx(800, ACTIVITY_HEIGHT_FIXED)
                    .build()
                customTabsIntent.launchUrl(context, url.toUri())
            }
        ) {
            Text("Bottom Sheet Custom Tabs")
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                val customTabsIntent = CustomTabsIntent.Builder()
                    .setInitialActivityHeightPx(400)
                    .setInitialActivityWidthPx(400)
                    .setActivitySideSheetBreakpointDp(800)
                    .build()
                customTabsIntent.launchUrl(context, url.toUri())
            }
        ) {
            Text("Side Sheet Custom Tabs")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun preview() {
    ComposeWebViewTheme {
        CustomTabsView()
    }
}