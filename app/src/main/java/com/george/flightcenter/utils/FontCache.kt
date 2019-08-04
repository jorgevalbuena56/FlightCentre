package com.george.flightcenter.utils

import android.content.Context
import android.graphics.Typeface
import java.util.Hashtable


/**
 * Utility class to cache custom fonts in order to avoid memory leaks
 */
object FontCache {
    private val fontCache = Hashtable<String, Typeface>()

    operator fun get(name: String, context: Context): Typeface? {
        return fontCache[name] ?: kotlin.run {
            val tf = Typeface.createFromAsset(context.assets, name)
            fontCache.put(name, tf)
            tf
        }
    }
}