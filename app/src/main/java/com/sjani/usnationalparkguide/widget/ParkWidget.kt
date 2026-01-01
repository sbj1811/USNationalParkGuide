package com.sjani.usnationalparkguide.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.sjani.usnationalparkguide.MainActivity
import com.sjani.usnationalparkguide.R

class ParkWidget : GlanceAppWidget() {
    
    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition
    
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                ParkWidgetContent()
            }
        }
    }
    
    @Composable
    private fun ParkWidgetContent() {
        val prefs = currentState<androidx.datastore.preferences.core.Preferences>()
        val parkName = prefs[stringPreferencesKey("fav_park_name")] ?: "No favorite park"
        val parkDesignation = prefs[stringPreferencesKey("fav_park_designation")] ?: "Tap to explore"
        
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .cornerRadius(16.dp)
                .background(ColorProvider(Color(0xFF2D5016)))
                .clickable(actionStartActivity<MainActivity>())
        ) {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = GlanceModifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        provider = ImageProvider(R.mipmap.ic_launcher),
                        contentDescription = "App Icon",
                        modifier = GlanceModifier.size(40.dp)
                    )
                    
                    Spacer(modifier = GlanceModifier.width(12.dp))
                    
                    Column {
                        Text(
                            text = "⭐ Favorite Park",
                            style = TextStyle(
                                color = ColorProvider(Color.White.copy(alpha = 0.8f)),
                                fontSize = 12.sp
                            )
                        )
                    }
                }
                
                Spacer(modifier = GlanceModifier.height(12.dp))
                
                Text(
                    text = parkName,
                    style = TextStyle(
                        color = ColorProvider(Color.White),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 2
                )
                
                Spacer(modifier = GlanceModifier.height(4.dp))
                
                Text(
                    text = parkDesignation,
                    style = TextStyle(
                        color = ColorProvider(Color.White.copy(alpha = 0.7f)),
                        fontSize = 12.sp
                    ),
                    maxLines = 1
                )
            }
        }
    }
}

class ParkWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ParkWidget()
}


