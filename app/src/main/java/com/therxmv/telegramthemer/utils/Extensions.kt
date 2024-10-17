package com.therxmv.telegramthemer.utils

import android.view.View

fun Boolean.toVisibility() = if(this) View.VISIBLE else View.GONE

fun checkVersionForMonet() = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S