package com.hmibrahimsarkar.smritilipi.ui.font

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import androidx.compose.ui.text.font.FontFamily
import java.util.concurrent.ConcurrentHashMap

/**
 * Robust FontLoader utility that programmatically loads custom TTF/OTF font
 * files from the assets folder and maps them to Jetpack Compose [FontFamily]
 * with safe error handling to prevent runtime crashes.
 */
object FontLoader {

    private const val TAG = "FontLoader"
    private const val DEFAULT_ASSET_FOLDER = "fonts"

    // Thread-safe cache to prevent redundant Typeface creations
    private val fontFamilyCache = ConcurrentHashMap<String, FontFamily>()
    private var appContext: Context? = null
    private var fontOptionMap: Map<String, BengaliFontOption> = emptyMap()

    /**
     * Programmatically loads a custom TTF/OTF font file from assets and converts it to a Compose [FontFamily].
     * Falls back to FontFamily.Default safely if the asset is missing or invalid.
     */
    fun loadFontFamilyFromAsset(
        context: Context,
        assetFileName: String,
        folderPath: String = DEFAULT_ASSET_FOLDER
    ): FontFamily {
        val fullPath = if (folderPath.isBlank()) assetFileName else "$folderPath/$assetFileName"
        fontFamilyCache[fullPath]?.let { return it }

        return try {
            val typeface = Typeface.createFromAsset(context.assets, fullPath)
            if (typeface != null) {
                val fontFamily = FontFamily(typeface)
                fontFamilyCache[fullPath] = fontFamily
                fontFamilyCache[assetFileName] = fontFamily
                fontFamily
            } else {
                FontFamily.Default
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load Typeface from asset: $fullPath", e)
            FontFamily.Default
        }
    }

    /**
     * Programmatically initializes font mapping and pre-caches the primary default fonts lazily.
     */
    fun initFonts(context: Context, options: List<BengaliFontOption>) {
        val ctx = context.applicationContext ?: context
        appContext = ctx
        fontOptionMap = options.associateBy { it.key.lowercase().trim() }

        // Lazily pre-load only the default primary font to keep startup instant and smooth
        val primaryFont = options.firstOrNull()
        if (primaryFont != null) {
            loadFontFamilyFromAsset(ctx, primaryFont.assetFileName)
        }
    }

    /**
     * Retrieves a Compose [FontFamily] by key, loading on-demand if not already cached.
     * Guaranteed to return a valid [FontFamily] (falls back to [FontFamily.Default]).
     */
    fun getFontFamily(key: String): FontFamily {
        val normalizedKey = key.lowercase().trim()
        fontFamilyCache[normalizedKey]?.let { return it }

        val ctx = appContext
        if (ctx != null) {
            val option = fontOptionMap[normalizedKey]
            val fileName = option?.assetFileName ?: "$normalizedKey.ttf"
            return loadFontFamilyFromAsset(ctx, fileName)
        }

        return fontFamilyCache["$DEFAULT_ASSET_FOLDER/$normalizedKey.ttf"]
            ?: fontFamilyCache["$DEFAULT_ASSET_FOLDER/$normalizedKey.otf"]
            ?: FontFamily.Default
    }

    /**
     * Clears all internal font caches.
     */
    fun clearCache() {
        fontFamilyCache.clear()
    }
}

