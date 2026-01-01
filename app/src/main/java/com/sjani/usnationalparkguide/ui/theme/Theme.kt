package com.sjani.usnationalparkguide.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    // Primary colors
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD4E8D6),
    onPrimaryContainer = PrimaryDark,
    
    // Secondary colors (Accent)
    secondary = Accent,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFDDD4),
    onSecondaryContainer = Color(0xFF5D2A1A),
    
    // Tertiary colors
    tertiary = InfoColor,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFD4E3F5),
    onTertiaryContainer = Color(0xFF1A3350),
    
    // Background & Surface
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFFF5EDE4),
    onSurfaceVariant = Muted,
    
    // Other
    outline = Divider,
    outlineVariant = Color(0xFFD9CFC3),
    error = ErrorColor,
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD9),
    onErrorContainer = Color(0xFF5C0F14),
    
    inverseSurface = TextPrimary,
    inverseOnSurface = Background,
    inversePrimary = DarkPrimary,
    
    scrim = Color.Black,
    surfaceTint = Primary
)

private val DarkColorScheme = darkColorScheme(
    // Primary colors
    primary = DarkPrimary,
    onPrimary = PrimaryDark,
    primaryContainer = Primary,
    onPrimaryContainer = Color(0xFFD4E8D6),
    
    // Secondary colors (Accent)
    secondary = DarkAccent,
    onSecondary = Color(0xFF5D2A1A),
    secondaryContainer = Color(0xFF8C4A38),
    onSecondaryContainer = Color(0xFFFFDDD4),
    
    // Tertiary colors
    tertiary = Color(0xFF9DB8D9),
    onTertiary = Color(0xFF1A3350),
    tertiaryContainer = Color(0xFF2D4A68),
    onTertiaryContainer = Color(0xFFD4E3F5),
    
    // Background & Surface
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkCard,
    onSurfaceVariant = DarkOnSurfaceVariant,
    
    // Other
    outline = Color(0xFF5A524C),
    outlineVariant = Color(0xFF3D3632),
    error = Color(0xFFFFB3B5),
    onError = Color(0xFF5C0F14),
    errorContainer = Color(0xFF8C1D28),
    onErrorContainer = Color(0xFFFFDAD9),
    
    inverseSurface = DarkOnSurface,
    inverseOnSurface = DarkBackground,
    inversePrimary = Primary,
    
    scrim = Color.Black,
    surfaceTint = DarkPrimary
)

@Composable
fun USNationalParkGuideTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Disable dynamic color to use our custom palette
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Set status bar and navigation bar to transparent for edge-to-edge
            // Only apply to older Android versions; Android 15+ handles this automatically
            @Suppress("DEPRECATION")
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                window.statusBarColor = android.graphics.Color.TRANSPARENT
                window.navigationBarColor = android.graphics.Color.TRANSPARENT
            }
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
