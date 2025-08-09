package com.oguzhanaslann.composewebview

import android.content.Intent
import android.graphics.Bitmap
import android.util.Base64
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewCompose(
    modifier: Modifier = Modifier,
    url: String,
) {
    var webView: WebView? = null
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                loadUrl(url)
                webView = this
            }
        }, update = {
            webView = it
        }
    )
}


@Composable
fun WebViewHtmlCompose(
    modifier: Modifier = Modifier,
    htmlContent: String,
    baseUrl: String = "about:blank"
) {
    var webView: WebView? = null
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                loadDataWithBaseURL(baseUrl, htmlContent, "text/html", "utf-8", null)
                webView = this
            }
        }, update = {
            webView = it
        }
    )
}


@Composable
fun WebViewHtmlCompose(
    modifier: Modifier = Modifier,
    htmlContent: String,
    mimetype: String,
    encoding: String
) {
    var webView: WebView? = null
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                val encodedHtml =
                    Base64.encodeToString(htmlContent.toByteArray(), Base64.NO_PADDING)
                loadData(encodedHtml, mimetype, encoding)
                webView = this
            }
        }, update = {
            webView = it
        }
    )
}

@Composable
fun WebViewHtmlAssetCompose(
    modifier: Modifier = Modifier,
    assetFileName: String
) {
    val assetUrl = "file:///android_asset/$assetFileName"
    var webView: WebView? = null
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                loadUrl(assetUrl)
                webView = this
            }
        }, update = {
            webView = it
        }
    )
}

@Preview
@Composable
fun previewWebView() {
    WebViewCompose(
        modifier = Modifier
            .border(4.dp, androidx.compose.ui.graphics.Color.Red),
        url = "https://www.google.com"
    )

}

@Preview
@Composable
private fun previewWebViewLoadData() {
    WebViewHtmlCompose(
        htmlContent =
            """
        <html>
            <body>
                <h1 style="color: #FF5722;">Hello, Compose WebView!</h1>
                <p style="color: #3F51B5;">This is a sample HTML content displayed inside the WebView.</p>
                <ul>
                    <li style="color: #4CAF50;">Feature 1: Load HTML content</li>
                    <li style="color: #FF9800;">Feature 2: Customize content dynamically</li>
                </ul>
            </body>
        </html>
    """.trimIndent(),
        mimetype = "text/html",
        encoding = "base64"
    )
}

@Preview
@Composable
private fun previewLoadDataWithBaseURL() {
    WebViewHtmlCompose(
        htmlContent =
            """
        <html>
            <body>
                <h1 style="color: #673AB7;">LoadDataWithBaseURL Preview</h1>
                <p style="color: #2196F3;">Demonstrating Base64 encoded HTML content rendering.</p>
                <p>Base URL: about:blank</p>
            </body>
        </html>
    """.trimIndent()
    )
}

@Preview
@Composable
private fun previewWebViewHtmlAsset() {
    WebViewHtmlAssetCompose(
        assetFileName = "sample.html"
    )
}

@Composable
fun WebViewHtmlAssetWithJavaScriptBinding(
    modifier: Modifier = Modifier,
    assetFileName: String
) {
    val assetUrl = "file:///android_asset/$assetFileName"
    var webView: WebView? = null
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                loadUrl(assetUrl)
                settings.javaScriptEnabled = true
                webView = this
                webView?.addJavascriptInterface(AppBridge(context), "NativeApp")
            }
        },
        update = {
            webView = it
            webView?.addJavascriptInterface(AppBridge(it.context), "NativeApp")
        }
    )
}


@Preview
@Composable
private fun previewWebViewHtmlAssetFunctionCall() {
    WebViewHtmlAssetWithJavaScriptBinding(
        assetFileName = "function_call_sample.html"
    )
}


@Composable
fun WebViewBackHandling(
    modifier: Modifier = Modifier,
    url: String,
) {
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null

    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                        backEnabled = view.canGoBack()
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        val urlToLoad = request?.url?.toString() ?: return false
                        if (urlToLoad.contains("medium.com")) {
                            return false
                        }
                        view?.context?.startActivity(Intent(Intent.ACTION_VIEW, request.url))
                        return true
                    }
                }
                loadUrl(url)
                webView = this
            }
        }, update = {
            webView = it
        })
}

@Preview
@Composable
private fun previewWebViewBackHandling() {
    WebViewBackHandling(
        modifier = Modifier
            .border(2.dp, androidx.compose.ui.graphics.Color.Blue),
        url = "https://www.google.com"
    )
}
