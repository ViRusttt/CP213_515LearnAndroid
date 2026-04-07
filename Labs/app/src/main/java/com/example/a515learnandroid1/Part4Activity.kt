package com.example.a515learnandroid1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a515learnandroid1.ui.theme._515LearnAndroid1Theme
import kotlin.math.roundToInt

class TodoViewModel : ViewModel() {
    val todoList = mutableStateListOf(
        "Buy groceries",
        "Finish Android Lab",
        "Wash the car",
        "Call mom",
        "Read a book"
    )

    fun removeItem(item: String) {
        todoList.remove(item)
    }
}

class Part4Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _515LearnAndroid1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GestureShowcaseScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestureShowcaseScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = viewModel()
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        item {
            Text("1. Tap / Double Tap / Long Press", color = Color.Gray)
            TapGestureExample()
        }

        item {
            Text("2. Drag Gesture", color = Color.Gray)
            DragGestureExample()
        }

        item {
            Text("3. Transform (Pinch to Zoom/Rotate)", color = Color.Gray)
            TransformGestureExample()
        }

        item {
            Text("4. Swipe-to-Dismiss (Todo List)", color = Color.Gray)
        }

        items(
            items = viewModel.todoList,
            key = { it } // สำคัญสำหรับการลบให้ถูกตำแหน่ง
        ) { item ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if (it == SwipeToDismissBoxValue.EndToStart) {
                        viewModel.removeItem(item)
                        true
                    } else {
                        false
                    }
                }
            )

            SwipeToDismissBox(
                state = dismissState,
                enableDismissFromStartToEnd = false,
                backgroundContent = {
                    val color = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                        Color.Red
                    } else {
                        Color.Transparent
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Icon",
                                tint = Color.White
                            )
                        }
                    }
                }
            ) {
                ListItem(
                    headlineContent = { Text(item) }
                )
            }
        }
    }
}

@Composable
fun TapGestureExample() {
    var text by remember { mutableStateOf("Tap Me") }
    var color by remember { mutableStateOf(Color.LightGray) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { /* Called when pointer is down */ },
                    onDoubleTap = {
                        text = "Double Tapped!"
                        color = Color.Cyan
                    },
                    onLongPress = {
                        text = "Long Pressed!"
                        color = Color.Magenta
                    },
                    onTap = {
                        text = "Single Tapped!"
                        color = Color.Yellow
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}

@Composable
fun DragGestureExample() {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color(0xFFEEEEEE))
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .size(80.dp)
                .background(Color.Blue)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text("Drag", color = Color.White)
        }
    }
}

@Composable
fun TransformGestureExample() {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFFDDDDDD)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .background(Color.Red)
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, rot ->
                        scale *= zoom
                        rotation += rot
                        offset += pan
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text("Transform\n(Pinch)", color = Color.White, textAlign = TextAlign.Center)
        }
    }
}
