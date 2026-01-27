package com.example.a515learnandroid1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a515learnandroid1.ui.theme._515LearnAndroid1Theme
import org.w3c.dom.Text

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            _515LearnAndroid1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "Android2",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
//            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
 Column {
     Text(
         text = "Hello $name!",
         modifier = modifier
     )
     Text(
         text = "Goodbye $name!",
         modifier = modifier
     )
 }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    _515LearnAndroid1Theme {
        Greeting2("Android")
    }
}