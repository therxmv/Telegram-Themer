package com.therxmv.telegramthemer.ui.animator

import android.animation.ObjectAnimator
import android.view.View

object SlideHorizontalAnimator {
    private const val DURATION = 300L

    fun inFromRight(fromPosition: Float, duration: Long = DURATION) = ObjectAnimator
        .ofFloat(this, View.TRANSLATION_X.name, fromPosition, 0f)
        .setDuration(duration)

    fun outToRight(toPosition: Float, duration: Long = DURATION) = ObjectAnimator
        .ofFloat(this, View.TRANSLATION_X.name, 0f, toPosition)
        .setDuration(duration)
}