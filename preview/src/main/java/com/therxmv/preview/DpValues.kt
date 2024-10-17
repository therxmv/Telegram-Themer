package com.therxmv.preview

import android.content.Context
import com.therxmv.preview.utils.dpToPx

class DpValues(
    private val context: Context,
    private val scaleFactor: Float,
) {

    val dp4: Int
        get() = 4.dpToPx(context, scaleFactor).toInt()

    val dp7: Int
        get() = 7.dpToPx(context, scaleFactor).toInt()

    val dp8: Int
        get() = 8.dpToPx(context, scaleFactor).toInt()

    val dp10: Int
        get() = 10.dpToPx(context, scaleFactor).toInt()

    val dp14: Int
        get() = 14.dpToPx(context, scaleFactor).toInt()

    val dp20: Int
        get() = 20.dpToPx(context, scaleFactor).toInt()

    val dp30: Int
        get() = 30.dpToPx(context, scaleFactor).toInt()

    val dp40: Int
        get() = 40.dpToPx(context, scaleFactor).toInt()

    val dp50: Int
        get() = 50.dpToPx(context, scaleFactor).toInt()

    val dp80: Int
        get() = 80.dpToPx(context, scaleFactor).toInt()

    val dp100: Int
        get() = 100.dpToPx(context, scaleFactor).toInt()
}