package com.hmibrahimsarkar.smritilipi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

@Composable
fun SmritilipiTheme(
    themeId: String = "smritilipi_classic",
    customThemes: List<AppThemeData> = emptyList(),
    isDarkMode: Boolean = true,
    fontSizePreference: String = "medium",
    content: @Composable () -> Unit
) {
    val themeData = AppThemes.getThemeById(themeId, customThemes)
    val finalColorScheme = themeData.colorSchemeFor(isDarkMode)

    val scaleMultiplier = when (fontSizePreference) {
        "small" -> 0.88f
        "large" -> 1.18f
        "extra_large" -> 1.32f
        else -> 1.0f
    }

    val currentDensity = LocalDensity.current
    val customDensity = Density(
        density = currentDensity.density,
        fontScale = currentDensity.fontScale * scaleMultiplier
    )

    CompositionLocalProvider(
        LocalDensity provides customDensity
    ) {
        MaterialTheme(
            colorScheme = finalColorScheme,
            shapes = AppShapes,
            typography = AppTypography,
            content = content
        )
    }
}

@Composable
fun KabyolokorTheme(
    themeId: String = "smritilipi_classic",
    customThemes: List<AppThemeData> = emptyList(),
    isDarkMode: Boolean = true,
    fontSizePreference: String = "medium",
    content: @Composable () -> Unit
) {
    SmritilipiTheme(
        themeId = themeId,
        customThemes = customThemes,
        isDarkMode = isDarkMode,
        fontSizePreference = fontSizePreference,
        content = content
    )
}
