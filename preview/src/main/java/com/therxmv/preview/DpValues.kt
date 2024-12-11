package com.therxmv.preview

import android.content.Context
import com.therxmv.preview.utils.dpToPx

/**
 * Provides scalable values converted from dp to px. Should be used with all custom views.
 */
class DpValues(
    private val context: Context,
    private val scaleFactor: Float = 1f,
) {

    val dp4: Int
        get() = dpOf(4)

    val dp7: Int
        get() = dpOf(7)

    val dp8: Int
        get() = dpOf(8)

    val dp10: Int
        get() = dpOf(10)

    val dp14: Int
        get() = dpOf(14)

    val dp20: Int
        get() = dpOf(20)

    val dp30: Int
        get() = dpOf(30)

    val dp40: Int
        get() = dpOf(40)

    val dp50: Int
        get() = dpOf(50)

    val dp60: Int
        get() = dpOf(60)

    val dp80: Int
        get() = dpOf(80)

    val dp100: Int
        get() = dpOf(100)

    val dp160: Int
        get() = dpOf(160)

    fun dpOf(value: Int) = value.dpToPx(context, scaleFactor).toInt()
}