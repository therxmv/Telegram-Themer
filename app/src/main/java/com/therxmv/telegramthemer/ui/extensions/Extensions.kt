package com.therxmv.telegramthemer.ui.extensions

import android.os.Build
import android.view.View

fun Boolean.toVisibility() = if(this) View.VISIBLE else View.GONE

fun isMonetAvailable() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S