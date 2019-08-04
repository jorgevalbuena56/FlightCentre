package com.george.flightcenter.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.george.flightcenter.utils.FontUtils

class FlightCenterTextView : AppCompatTextView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        if (!isInEditMode) {
            FontUtils.setCustomFont(this, context, attrs)
        }
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        if (!isInEditMode) {
            FontUtils.setCustomFont(this, context, attrs)
        }
    }
}