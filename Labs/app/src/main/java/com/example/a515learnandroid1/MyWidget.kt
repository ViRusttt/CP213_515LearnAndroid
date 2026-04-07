package com.example.a515learnandroid1

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

class MyWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                MyContent()
            }
        }
    }

    @Composable
    private fun MyContent() {
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(GlanceTheme.colors.background)
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = "Weather Widget",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = GlanceTheme.colors.onBackground
                    )
                )
                Spacer(modifier = GlanceModifier.height(8.dp))
                Text(
                    text = "Bangkok: 32°C ☀️",
                    style = TextStyle(color = GlanceTheme.colors.onSurfaceVariant)
                )
            }
        }
    }
}

class MyWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MyWidget()
}
