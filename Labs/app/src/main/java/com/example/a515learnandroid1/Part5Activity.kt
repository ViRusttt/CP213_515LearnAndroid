package com.example.a515learnandroid1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a515learnandroid1.ui.theme._515LearnAndroid1Theme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SnackbarViewModel : ViewModel() {
    // ใช้ Channel สำหรับส่งข้อมูลแบบ One-time event หลีกเลี่ยงปัญหา Re-trigger เมื่อหมุนหน้าจอ
    private val _errorChannel = Channel<String>()
    val errorFlow = _errorChannel.receiveAsFlow()

    fun triggerError() {
        viewModelScope.launch {
            _errorChannel.send("An unexpected error occurred!")
        }
    }
}

class Part5Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _515LearnAndroid1Theme {
                SnackbarScreen()
            }
        }
    }
}

@Composable
fun SnackbarScreen(viewModel: SnackbarViewModel = viewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }

    // ทำงานภายใต้ Coroutine Scope ของ Compose
    // รอดักฟังค่า Event จาก ViewModel (Side Effect) ทำงานแยกจาก Recomposition ทั่วไป
    LaunchedEffect(key1 = true) {
        viewModel.errorFlow.collect { errorMessage ->
            snackbarHostState.showSnackbar(message = errorMessage)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { viewModel.triggerError() }) {
                Text("Trigger Error")
            }
        }
    }
}
