package com.therxmv.telegramthemer.utils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.therxmv.preview.utils.dpToPx
import com.therxmv.telegramthemer.R

fun Boolean.toVisibility() = if(this) View.VISIBLE else View.GONE

fun isMonetAvailable() = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S

fun Drawable.startRadiusAnimation(context: Context) {
    if (this !is GradientDrawable) return

    val startRadius = context.resources.getDimension(R.dimen.button_10_radius)
    val endRadius = 100.dpToPx(context)

    val animator = ValueAnimator.ofFloat(startRadius, endRadius, startRadius).apply {
        duration = 350L
        addUpdateListener {
            val radius = it.animatedValue as Float
            this@startRadiusAnimation.cornerRadius = radius
        }
    }

    animator.start()
}