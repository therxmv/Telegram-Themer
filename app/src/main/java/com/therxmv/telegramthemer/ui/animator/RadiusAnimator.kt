package com.therxmv.telegramthemer.ui.animator

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import com.therxmv.preview.utils.dpToPx
import com.therxmv.telegramthemer.R

object RadiusAnimator {
    private const val DURATION = 500L
    private const val CIRCLE_RADIUS = 100

    fun Drawable.animateToCircle(context: Context, duration: Long = DURATION) {
        val startRadius = context.resources.getDimension(R.dimen.button_10_radius)
        val endRadius = CIRCLE_RADIUS.dpToPx(context)

        animateRadius(startRadius, endRadius, duration)
    }

    private fun Drawable.animateRadius(startRadius: Float, endRadius: Float, duration: Long = DURATION) {
        if (this !is GradientDrawable) return

        val animator = ValueAnimator.ofFloat(startRadius, endRadius, startRadius).apply {
            this.duration = duration
            addUpdateListener {
                val radius = it.animatedValue as Float
                this@animateRadius.cornerRadius = radius
            }
        }

        animator.start()
    }
}