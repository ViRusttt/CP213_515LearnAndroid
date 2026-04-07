package com.example.a515learnandroid1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a515learnandroid1.ui.theme._515LearnAndroid1Theme
import kotlinx.coroutines.delay

class Part11Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _515LearnAndroid1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SkeletonLoadingScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun skeletonBrush(showShimmer: Boolean = true): Brush {
    return if (showShimmer) {
        val transition = rememberInfiniteTransition(label = "shimmer")
        val translateAnim by transition.animateFloat(
            initialValue = 0f,
            targetValue = 2000f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1500, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "shimmer_anim"
        )
        val colors = listOf(Color.LightGray.copy(alpha = 0.5f), Color.LightGray.copy(alpha = 0.2f), Color.LightGray.copy(alpha = 0.5f))
        Brush.linearGradient(
            colors = colors,
            start = Offset.Zero,
            end = Offset(x = translateAnim, y = translateAnim)
        )
    } else {
        Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
    }
}

@Composable
fun SkeletonLoadingScreen(modifier: Modifier = Modifier) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(3000) // จำลองการโหลดข้อมูล 3 วินาที
            isLoading = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Concept: Skeleton Loading",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Skeleton Loading (หรือ Shimmer Effect) คือการแสดงโครงร่าง (Placeholder) ของ UI แบบเคลื่อนไหวชั่วคราวระหว่างรอข้อมูลโหลดเสร็จจาก Backend \n\n" +
                   "ข้อดี: ช่วยลดความหงุดหงิดของผู้ใช้ สร้างความรู้สึกว่าเซิร์ฟเวอร์ตอบสนองเร็ว และทำให้ผู้ใช้รู้ว่า Content ชุดต่อไปจะมีหน้าตาและ Layout ประมาณไหน แทนที่จะปล่อยจอโล่ง ๆ หรือใช้ Progress Indicator วงกลมหมุนๆ",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Toggle Button
        Button(onClick = { isLoading = !isLoading }) {
            Text(if (isLoading) "Cancel Loading" else "Restart Loading (3s)")
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(5) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    // Profile Image Skeleton
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(if (isLoading) skeletonBrush() else SolidColor(Color(0xFF6200EA)))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    // Texts Skeleton
                    Column(modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(20.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isLoading) skeletonBrush() else SolidColor(Color.Transparent))
                        ) {
                            if (!isLoading) Text("User Name", fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isLoading) skeletonBrush() else SolidColor(Color.Transparent))
                        ) {
                            if (!isLoading) Text("This is a fetched description.", color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}
