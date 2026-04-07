package com.example.a515learnandroid1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a515learnandroid1.ui.theme._515LearnAndroid1Theme

class Part8Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _515LearnAndroid1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ResponsiveProfileScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ResponsiveProfileScreen(modifier: Modifier = Modifier) {
    // ใช้ BoxWithConstraints เพื่อเข้าถึง maxWidth/maxHeight ของหน้าจอ
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (maxWidth < 600.dp) {
            // โหมดแนวตั้ง / หน้าจอเล็ก
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfilePicture()
                Spacer(modifier = Modifier.height(24.dp))
                ProfileInfo()
            }
        } else {
            // โหมดแนวนอน / หน้าจอใหญ่ (เกิน 600.dp)
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfilePicture()
                Spacer(modifier = Modifier.width(32.dp))
                Box(modifier = Modifier.weight(1f)) {
                    ProfileInfo()
                }
            }
        }
    }
}

@Composable
fun ProfilePicture() {
    Box(
        modifier = Modifier
            .size(150.dp)
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Profile Pic", color = Color.White)
    }
}

@Composable
fun ProfileInfo() {
    Column {
        Text(text = "John Doe", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Software Engineer", fontSize = 18.sp, color = Color.DarkGray)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "This is a brief description of the user. It can span multiple lines to demonstrate how the layout reacts properly in both Row and Column scenarios.")
    }
}
