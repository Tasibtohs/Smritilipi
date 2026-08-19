package com.hmibrahimsarkar.smritilipi.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ================= SMRITILIPI BRAND DESIGN TOKENS =================
// CSS Variables Mapped:
// --color-primary: #0B1F3A
// --color-accent: #C9A857
// --color-bg-light: #FDFBF6
// --color-bg-dark: #0A1628
// --color-text-primary-light: #1C1C1E
// --color-text-primary-dark: #F5F5F5
// --color-text-secondary: #6B6B6B
// --color-success: #4CAF7D
// --color-error: #C0392B

val NavyPrimary = Color(0xFF0B1F3A)        // --color-primary
val NavyDark = Color(0xFF0B1F3A)           // Header, Appbar, Main Button
val NavyMid = Color(0xFF182F52)            // Elevated surface variant
val NavyBlack = Color(0xFF0A1628)          // --color-bg-dark
val NavySurface = Color(0xFF11223E)        // Elevated Navy Card Surface
val NavySurfaceVariant = Color(0xFF182F52) // Navy Surface Variant
val NavyBorder = Color(0xFF203B64)         // Border for cards/inputs in dark

// Accent Gold
val GoldAccent = Color(0xFFC9A857)         // --color-accent
val GoldPrimary = Color(0xFFC9A857)        // Icon, Highlight, Border, Selected State
val AmberAccent = Color(0xFFC9A857)        // Accent
val GoldLight = Color(0xFFE2C87A)          // Soft highlight
val GoldDark = Color(0xFFA88939)           // Deep Gold Accent
val GoldGlow = Color(0xFFE2C87A)           // Glow accent

// Functional State Colors
val SuccessGreen = Color(0xFF4CAF7D)       // --color-success
val ErrorRed = Color(0xFFC0392B)           // --color-error

// Light Mode Canvas & Text
val LightBackground = Color(0xFFFDFBF6)     // --color-bg-light
val LightSurface = Color(0xFFFFFFFF)        // Pure White card surface
val LightSurfaceVariant = Color(0xFFF4F0E6) // Warm surface variant
val LightBorder = Color(0xFFE5DECE)         // Subtle warm border
val LightTextPrimary = Color(0xFF1C1C1E)    // --color-text-primary-light
val LightTextSecondary = Color(0xFF6B6B6B)  // --color-text-secondary

// Dark Mode Canvas & Text
val DarkBackground = Color(0xFF0A1628)      // --color-bg-dark
val DarkSurface = Color(0xFF11223E)         // Elevated Navy Card
val DarkSurfaceVariant = Color(0xFF182F52)  // Surface variant
val DarkBorder = Color(0xFF203B64)          // Deep Navy outline
val DarkTextPrimary = Color(0xFFF5F5F5)     // --color-text-primary-dark
val DarkTextSecondary = Color(0xFF9E9EA8)   // Secondary text in dark mode

// Gradients & Shading
val GoldGradient = Brush.horizontalGradient(
    colors = listOf(GoldAccent, GoldDark)
)

val GoldGlowGradient = Brush.radialGradient(
    colors = listOf(GoldLight.copy(alpha = 0.45f), Color.Transparent)
)

val NavyGlassBrush = Brush.verticalGradient(
    colors = listOf(
        NavySurface.copy(alpha = 0.92f),
        NavyDark.copy(alpha = 0.85f)
    )
)

// Additional UI Accent Colors
val SoftLavender = Color(0xFFC9B3E8)
val LavenderDark = Color(0xFF8A6CB3)
val MutedGrey = Color(0xFF8A8A9E)
val RoseAccent = Color(0xFFE57373)
val EmeraldGreen = Color(0xFF81C784)

@androidx.compose.runtime.Composable
fun resolveAdaptiveTextColor(hexString: String): Color {
    val defaultOnSurface = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
    val bg = androidx.compose.material3.MaterialTheme.colorScheme.background
    val bgLuminance = bg.red * 0.2126f + bg.green * 0.7152f + bg.blue * 0.0722f
    val isDarkTheme = bgLuminance < 0.5f

    if (hexString.isBlank() || hexString.equals("#1A1A2E", ignoreCase = true) || hexString.equals("DEFAULT", ignoreCase = true)) {
        return defaultOnSurface
    }
    return try {
        val parsed = Color(android.graphics.Color.parseColor(hexString))
        val luminance = parsed.red * 0.2126f + parsed.green * 0.7152f + parsed.blue * 0.0722f

        if (isDarkTheme && luminance < 0.35f) {
            defaultOnSurface
        } else if (!isDarkTheme && luminance > 0.75f) {
            defaultOnSurface
        } else {
            parsed
        }
    } catch (e: Exception) {
        defaultOnSurface
    }
}

@androidx.compose.runtime.Composable
fun resolveAdaptiveTitleColor(hexString: String): Color {
    val bg = androidx.compose.material3.MaterialTheme.colorScheme.background
    val bgLuminance = bg.red * 0.2126f + bg.green * 0.7152f + bg.blue * 0.0722f
    val isDarkTheme = bgLuminance < 0.5f
    val defaultTitleColor = if (isDarkTheme) GoldPrimary else GoldDark
    val defaultOnSurface = androidx.compose.material3.MaterialTheme.colorScheme.onSurface

    if (hexString.isBlank() || hexString.equals("DEFAULT", ignoreCase = true)) {
        return defaultTitleColor
    }
    if (hexString.equals("#1A1A2E", ignoreCase = true)) {
        return defaultOnSurface
    }
    return try {
        val parsed = Color(android.graphics.Color.parseColor(hexString))
        val luminance = parsed.red * 0.2126f + parsed.green * 0.7152f + parsed.blue * 0.0722f

        if (isDarkTheme && luminance < 0.35f) {
            defaultTitleColor
        } else if (!isDarkTheme && luminance > 0.85f) {
            defaultTitleColor
        } else {
            parsed
        }
    } catch (e: Exception) {
        defaultTitleColor
    }
}

