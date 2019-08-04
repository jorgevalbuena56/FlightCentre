package com.george.flightcenter.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan
import android.util.LruCache

/**
 * Style a [Spannable] with a custom [Typeface].
 * @author Tristan Waddington
 */
class TypeFaceSpan
/**
 * Load the [Typeface] and apply to a [Spannable].
 */
    (context: Context, typefaceName: String) : MetricAffectingSpan() {

    private var mTypeface: Typeface? = null

    init {
        mTypeface = sTypefaceCache.get(typefaceName)

        mTypeface = mTypeface ?: kotlin.run {
            mTypeface = Typeface.createFromAsset(context.applicationContext
                .assets, String.format("%s", typefaceName))

            // Cache the loaded Typeface
            sTypefaceCache.put(typefaceName, mTypeface)
        }
    }

    override fun updateMeasureState(p: TextPaint) {
        p.apply {
            typeface = mTypeface
            flags = flags or Paint.SUBPIXEL_TEXT_FLAG
        }
    }

    override fun updateDrawState(tp: TextPaint) {
        tp.apply {
            typeface = mTypeface
            flags = flags or Paint.SUBPIXEL_TEXT_FLAG
        }
    }

    companion object {
        /** An `LruCache` for previously loaded typefaces.  */
        private val sTypefaceCache = LruCache<String, Typeface>(12)
    }
}