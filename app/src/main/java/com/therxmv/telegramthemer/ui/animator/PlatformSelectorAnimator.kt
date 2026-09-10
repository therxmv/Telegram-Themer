package com.therxmv.telegramthemer.ui.animator

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.widget.TextView

/**
 * Animations for a sliding segmented-control-style selector (e.g. the
 * Android/iOS platform switch): a pill indicator that slides between two
 * slots, with its label text cross-fading in sync.
 */
object PlatformSelectorAnimator {
    private const val DURATION = 220L

    /** Slides this View (the selection pill) from wherever it currently sits to [toX]. */
    fun View.animateSlideTo(toX: Float, duration: Long = DURATION) {
        ObjectAnimator.ofFloat(this, View.TRANSLATION_X, translationX, toX)
            .setDuration(duration)
            .start()
    }

    /** Cross-fades this TextView's color from its current color to [toColor]. */
    fun TextView.animateTextColorTo(toColor: Int, duration: Long = DURATION) {
        ValueAnimator.ofArgb(currentTextColor, toColor).apply {
            this.duration = duration
            addUpdateListener { setTextColor(it.animatedValue as Int) }
            start()
        }
    }
}
