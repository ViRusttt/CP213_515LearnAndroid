package com.example.a515learnandroid1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a515learnandroid1.ui.theme._515LearnAndroid1Theme
import kotlinx.coroutines.launch

class Part12Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _515LearnAndroid1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DialogAndBottomSheetScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAndBottomSheetScreen(modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Concept: Modal Bottom Sheet \n& Middle Dialog",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "1. Dialog (Middle): หน้าต่างเด้งขึ้นมากลางจอ ใช้กับการขัดจังหวะผู้ใช้เพื่อขอการตัดสินใจสำคัญด่วนๆ เช่น Confirm Delete หรือ Warning ปิดกั้นการกระทำอื่นจนกว่าจะตอบ\n\n" +
                   "2. Modal Bottom Sheet: แผ่นที่สไลด์ตัวขึ้นมาจากขอบด้านล่างจอ ใช้แสดงเครื่องมือย่อย รายการเมนู หรือข้อมูลเพิ่มเติมที่ไม่ขัดจังหวะผู้ใช้อย่างรุนแรงเท่า Dialog และสามารถเข้าถึงด้วยนิ้วโป้งเดียว (Thumb-friendly) ได้ง่าย",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Show Middle Dialog")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = { showBottomSheet = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Show Modal Bottom Sheet")
        }
    }

    // 1. Middle Dialog Implementation
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmation") },
            text = { Text("Are you sure you want to perform this risky action? This dialog prevents interaction with the background UI until you decide.") },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) { Text("Confirm") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            }
        )
    }

    // 2. Modal Bottom Sheet Implementation
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Bottom Sheet Content", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Text("This is natively supported by Compose Material 3. You can place lists, forms, or actions here natively without navigating to a new screen. You can Drag down to dismiss it intuitively.")
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = { 
                    // ปิดอย่าง Smooth ด้วยการเรียก hide() ผ่าน Coroutine ก่อนที่จะปิดตัว state ให้เป็น false
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion { 
                        if (!sheetState.isVisible) showBottomSheet = false 
                    }
                }) {
                    Text("Close gracefully")
                }
            }
        }
    }
}
