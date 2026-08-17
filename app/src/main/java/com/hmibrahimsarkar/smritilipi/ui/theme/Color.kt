package com.hmibrahimsarkar.smritilipi.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ================= BRAND COLOR TOKENS: NAVY & GOLD =================
// Navy Blue Shades
val NavyDark = Color(0xFF0A1F44)           // গাঢ় নেভি ব্লু (#0A1F44)
val NavyMid = Color(0xFF1B3A6B)            // মিড-টোন নেভি ব্লু (#1B3A6B)
val NavyBlack = Color(0xFF050B18)          // গাঢ় নেভি-ব্ল্যাক ব্যাকগ্রাউন্ড (#050B18 - Dark Mode Bg)
val NavySurface = Color(0xFF0D1B33)        // এলিভেটেড নেভি সারফেস (#0D1B33 - Dark Mode Surface)
val NavySurfaceVariant = Color(0xFF152646) // নেভি সারফেস ভেরিয়েন্ট (#152646)
val NavyBorder = Color(0xFF1E3A68)         // নেভি বর্ডার (#1E3A68)

// Gold Shades
val GoldPrimary = Color(0xFFD4AF37)        // উজ্জ্বল সোনালি এক্সেন্ট (#D4AF37 - Bright Gold)
val AmberAccent = Color(0xFFD4AF37)        // Primary Accent
val GoldLight = Color(0xFFF0D584)          // হালকা সোনালি হাইলাইট ও গ্লো (#F0D584 - Light Gold Highlight)
val GoldDark = Color(0xFFB8860B)           // গাঢ় গোল্ড / শ্যাডো / লাইট মোড এক্সেন্ট (#B8860B)
val GoldGlow = Color(0xFFF0D584)           // গোল্ড গ্লো (#F0D584)

// Light Mode Tokens (Navy-tinted light theme)
val LightBackground = Color(0xFFF4F6FA)     // নেভি-টিন্টেড হালকা ব্যাকগ্রাউন্ড (#F4F6FA)
val LightSurface = Color(0xFFFFFFFF)        // হোয়াইট এলিভেটেড সারফেস
val LightSurfaceVariant = Color(0xFFE8EDF5) // নেভি-টিন্টেড সারফেস ভেরিয়েন্ট
val LightBorder = Color(0xFFCBD5E1)         // হালকা বর্ডার
val LightTextPrimary = Color(0xFF0A1F44)    // গাঢ় নেভি টেক্সট (#0A1F44 - ব্র্যান্ডিং কনসিস্টেন্ট)
val LightTextSecondary = Color(0xFF425678)  // মিউটেড নেভি টেক্সট

// Dark Mode Tokens (Deep Navy + Gold)
val DarkBackground = Color(0xFF050B18)      // গাঢ় নেভি-ব্ল্যাক (#050B18)
val DarkSurface = Color(0xFF0D1B33)         // এলিভেটেড নেভি সারফেস (#0D1B33)
val DarkSurfaceVariant = Color(0xFF152646)  // নেভি সারফেস ভেরিয়েন্ট
val DarkBorder = Color(0xFF1E3A68)          // নেভি বর্ডার
val DarkTextPrimary = Color(0xFFF5F0E1)     // হালকা ক্রিম টেক্সট (#F5F0E1 - Pure white নয়)
val DarkTextSecondary = Color(0xFFB0BBD0)   // মিউটেড নেভি-গ্রে টেক্সট

// Gold Gradients
val GoldGradient = Brush.horizontalGradient(
    colors = listOf(GoldPrimary, GoldDark)
)

val GoldGlowGradient = Brush.radialGradient(
    colors = listOf(GoldLight.copy(alpha = 0.5f), Color.Transparent)
)

val NavyGlassBrush = Brush.verticalGradient(
    colors = listOf(
        NavySurface.copy(alpha = 0.88f),
        NavyDark.copy(alpha = 0.72f)
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

