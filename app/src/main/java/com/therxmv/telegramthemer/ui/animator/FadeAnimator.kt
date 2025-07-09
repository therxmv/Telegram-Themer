package com.therxmv.telegramthemer.ui.animator

import android.animation.ObjectAnimator
import android.view.View

object FadeAnimator {
    private const val DURATION = 500L

    fun inside(duration: Long = DURATION) = ObjectAnimator
        .ofFloat(this, View.ALPHA.name, 0f, 1f)
        .setDuration(duration)

    fun outside(duration: Long = DURATION) = ObjectAnimator
        .ofFloat(this, View.ALPHA.name, 1f, 0f)
        .setDuration(duration)
}