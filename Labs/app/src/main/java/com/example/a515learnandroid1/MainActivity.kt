package com.example.a515learnandroid1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a515learnandroid1.ui.theme._515LearnAndroid1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray)
        ) {
//hp
            Box(
                modifier =
                    Modifier.height(32.dp)
                        .background(color = Color.White)

            ){
                Text(
                    text = "hp",
                    modifier = Modifier
                        .align(alignment - alignment.Center)
                        .fillMaxWidth(fration = 0.5f)
                        .background(color = color.red)
                        .padding(8.dp)
                )

            }
        }
    }
//image

//status
}
}