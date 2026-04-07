package com.example.a515learnandroid1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a515learnandroid1.ui.theme._515LearnAndroid1Theme

class Part10Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _515LearnAndroid1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppWidgetConceptScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppWidgetConceptScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Concept: App Widget",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "App Widget ไม่ใช่หน้าจอ Component ธรรมดาในแอป แต่เป็น 'Miniature Application View' ที่ไปฝังตัวอยู่บน Home Screen (หน้าจอหลัก) ของอุปกรณ์\n\n" +
                   "หลักการทำงานที่ควรทราบ:\n" +
                   "1. ในยุคเดิมเราจะต้องสร้างคลาสสืบทอดจาก AppWidgetProvider (เป็น BroadcastReceiver ชนิดหนึ่ง) และวาด UI ผ่านข้อจำกัดของ RemoteViews (สร้างด้วย XML)\n" +
                   "2. ปัจจุบันแนะนำให้ใช้ 'Jetpack Glance' ซึ่งถูกออกแบบมาให้คลาสและ Syntax คล้ายกับ Compose ทำให้เขียน UI สำหรับ Widget ด้วย Kotlin ได้ง่ายขึ้น\n" +
                   "3. ข้อมูลใน Widget อัปเดตผ่านระบบ OS ไม่ได้ตั้งใจให้แสดงผลแบบ Real-time (เช่น 60fps Animation) ทั้งนี้เพื่อประหยัดแบตเตอรี่\n\n" +
                   "ด้านล่างนี้คือ 'Mockup' จำลองหน้าตาของ Widget บน Home Screen ไม่ใช่ Widget จริงที่ติดตั้งบน Launcher:",
            style = MaterialTheme.typography.bodyLarge
        )

        // Mockup Widget Dashboard UI
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text("Weather Widget (Mockup)", fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Bangkok: 32°C ☀️", color = Color.DarkGray)
            }
        }
    }
}
