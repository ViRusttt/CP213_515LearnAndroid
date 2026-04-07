package com.example.a515learnandroid1

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a515learnandroid1.ui.theme._515LearnAndroid1Theme

class WebViewViewModel : ViewModel() {
    var url by mutableStateOf("https://www.google.com")
        private set

    fun updateUrl(newUrl: String) {
        val validUrl = if (!newUrl.startsWith("http://") && !newUrl.startsWith("https://")) {
            "https://$newUrl"
        } else {
            newUrl
        }
        url = validUrl
    }
}

class Part6Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _515LearnAndroid1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WebViewScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    modifier: Modifier = Modifier,
    viewModel: WebViewViewModel = viewModel()
) {
    var inputText by remember { mutableStateOf(viewModel.url) }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                label = { Text("URL") }
            )
            Button(
                onClick = { viewModel.updateUrl(inputText) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Go")
            }
        }

        // ฝัง WebView ด้วย AndroidView
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                // เรียกตอนสร้างครั้งแรก
                WebView(context).apply {
                    webViewClient = WebViewClient() // กันบราวเซอร์เด้งออกแอป
                    settings.javaScriptEnabled = true
                }
            },
            update = { webView ->
                // โค้ดในนี้จะถูกเรียกเมื่อ State ที่ถูกอ่าน (ในที่นี้คือ viewModel.url) มีการเปลี่ยนแปลง
                webView.loadUrl(viewModel.url)
            }
        )
    }
}
