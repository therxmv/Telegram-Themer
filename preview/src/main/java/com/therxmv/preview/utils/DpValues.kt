package com.therxmv.preview.utils

import android.content.Context

/**
 * Provides scalable values converted from dp to px. Should be used with all custom views.
 */
class DpValues(
    private val context: Context,
    private val scaleFactor: Float = 1f,
) {

    fun pxOf(dp: Int) = dp.dpToPx(context, scaleFactor).toInt()

    val dp4: Int
        get() = pxOf(4)

    val dp7: Int
        get() = pxOf(7)

    val dp8: Int
        get() = pxOf(8)

    val dp10: Int
        get() = pxOf(10)

    val dp14: Int
        get() = pxOf(14)

    val dp20: Int
        get() = pxOf(20)

    val dp30: Int
        get() = pxOf(30)

    val dp40: Int
        get() = pxOf(40)

    val dp50: Int
        get() = pxOf(50)

    val dp60: Int
        get() = pxOf(60)

    val dp80: Int
        get() = pxOf(80)

    val dp100: Int
        get() = pxOf(100)
}