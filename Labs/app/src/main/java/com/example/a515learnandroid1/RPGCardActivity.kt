package com.example.a515learnandroid1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a515learnandroid1.ui.theme._515LearnAndroid1Theme

class RPGCardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// enableEdgeToEdge()
        setContent {
            RPGCardView(
                onNextActivity = {
                    startActivity(Intent(this@RPGCardActivity, PokedexActivity::class.java))
                }
            )
        }
    }
}

@Composable
fun RPGCardView(onNextActivity: () -> Unit) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Gray)
                    .padding(32.dp)
            ) {

// HP
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .background(color = Color.White)
                ) {
                    Text(
                        text = "HP",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterStart)
                            .fillMaxWidth(fraction = 0.5f)
                            .background(color = Color.Red)
                            .padding(8.dp)
                    )
                }

// Image
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp)
                        .clickable {
                            onNextActivity.invoke()
                        }
                )
                var str by remember { mutableStateOf(9) }
                var agi by remember { mutableStateOf(9) }
                var int by remember { mutableStateOf(1) }
                var idk by remember { mutableStateOf(78) }
// Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Button(onClick = {
                            str = str + 1
                        }) {
                            Image(
                                painter = painterResource(R.drawable.outline_arrow_drop_up_24),
                                contentDescription = "up",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(text = "Str", fontSize = 20.sp)
                        Text(text = str.toString(), fontSize = 20.sp)
                        Button(onClick = {
                            str = str - 1
                        }) {
                            Image(
                                painter = painterResource(R.drawable.outline_arrow_drop_down_24),
                                contentDescription = "down",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Column {
                        Button(onClick = {
                            agi = agi + 1
                        }) {
                            Image(
                                painter = painterResource(R.drawable.outline_arrow_drop_up_24),
                                contentDescription = "up",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(text = "Agi", fontSize = 20.sp)
                        Text(text = agi.toString(), fontSize = 20.sp)
                        Button(onClick = {
                            agi = agi - 1
                        }) {
                            Image(
                                painter = painterResource(R.drawable.outline_arrow_drop_down_24),
                                contentDescription = "down",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Column {
                        Button(onClick = {
                            int = int + 1
                        }) {
                            Image(
                                painter = painterResource(R.drawable.outline_arrow_drop_up_24),
                                contentDescription = "up",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(text = "Int", fontSize = 20.sp)
                        Text(text = int.toString(), fontSize = 20.sp)
                        Button(onClick = {
                            int = int - 1
                        }) {
                            Image(
                                painter = painterResource(R.drawable.outline_arrow_drop_down_24),
                                contentDescription = "down",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Column {
                        Button(onClick = {
                            idk = idk + 1
                        }) {
                            Image(
                                painter = painterResource(R.drawable.outline_arrow_drop_up_24),
                                contentDescription = "up",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(text = "Idk", fontSize = 20.sp)
                        Text(text = idk.toString(), fontSize = 20.sp)
                        Button(onClick = {
                            idk = idk - 1
                        }) {
                            Image(
                                painter = painterResource(R.drawable.outline_arrow_drop_down_24),
                                contentDescription = "down",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

@Preview
@Composable
fun PreviewScreen() {
    RPGCardView({})
}




