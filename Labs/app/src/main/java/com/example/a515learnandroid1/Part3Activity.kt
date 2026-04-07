package com.example.a515learnandroid1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a515learnandroid1.ui.theme._515LearnAndroid1Theme

class Part3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _515LearnAndroid1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        DonutChart(
                            percentages = listOf(30f, 40f, 30f),
                            colors = listOf(Color.Red, Color.Green, Color.Blue),
                            modifier = Modifier.size(200.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DonutChart(
    percentages: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val animateSweepAngle by animateFloatAsState(
        targetValue = if (animationPlayed) 360f else 0f,
        animationSpec = tween(
            durationMillis = 1500,
            delayMillis = 0,
            easing = FastOutSlowInEasing
        ),
        label = "DonutAnimation"
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Canvas(modifier = modifier) {
        val total = percentages.sum()
        var startAngle = -90f
        val strokeWidth = 60f // กำหนดความหนาของเส้น (ทำให้เป็นโดนัท)

        for (i in percentages.indices) {
            val sweepAngle = (percentages[i] / total) * animateSweepAngle
            drawArc(
                color = colors[i],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false, // เว้นตรงกลางเพื่อเป็นโดนัท
                style = Stroke(width = strokeWidth)
            )
            startAngle += sweepAngle
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DonutChartPreview() {
    _515LearnAndroid1Theme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            DonutChart(
                percentages = listOf(30f, 40f, 30f),
                colors = listOf(Color.Red, Color.Green, Color.Blue),
                modifier = Modifier.size(200.dp)
            )
        }
    }
}