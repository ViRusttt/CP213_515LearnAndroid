package com.example.a515learnandroid1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.a515learnandroid1.ui.theme._515LearnAndroid1Theme

class Part9Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _515LearnAndroid1Theme {
                CollapsingToolbarScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbarScreen() {
    // 1. นำ Scroll Behavior ของ Material 3 มาใช้
    // แบบ exitUntilCollapsedScrollBehavior(): เมื่อเลื่อนหน้าจอลงเรื่อยๆ บาร์จะหดจนถึงขนาดที่กำหนดไว้ และจะลอยอยู่ติดขอบบน ไม่หายไปจนหมด
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // 2. ผูก Nested Scroll ของ Scaffold ให้เข้ากับ scrollBehavior 
    // สิ่งนี้จะทำให้เวลาเราลาก LazyColumn ตัว Scaffold จะบอกให้ TopAppBar รับรู้และจัดการหด/ขยาย
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            // ใช้ LargeTopAppBar ซึ่งรองรับการหดจากขนาดใหญ่ -> เล็ก
            LargeTopAppBar(
                title = { Text("Collapsing Toolbar") },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text(
                    text = "Concept ของการสร้าง Collapsing ใน Jetpack Compose:\n\n" +
                           "1. เราไม่ต้องเขียน Listener ในการผูก View ชิ้นต่อชิ้นแบบใน Android Native(XML) เดิม\n" +
                           "2. เราแค่อาศัย 'TopAppBarDefaults.exitUntilCollapsedScrollBehavior()'\n" +
                           "3. จากนั้นสั่งให้ Modifier.nestedScroll() นำพฤติกรรมนี้เสียบเข้ากับ Scaffold\n" +
                           "4. เมื่อพื้นที่เนื้อหาภายใน (ตัวอย่างคือ LazyColumn ด้านล่างนี้) มีการถูก Scroll (ไถหน้าจอ) มันก็จะเชื่อมต่อกัน และจัดการลดขนาด LargeTopAppBar ให้กลายเป็นไซส์ปกติแบบเนียนตา",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            
            items(40) { index ->
                Text(
                    text = "ตัวอย่างรายการที่ ${index + 1}",
                    modifier = Modifier.padding(vertical = 12.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
