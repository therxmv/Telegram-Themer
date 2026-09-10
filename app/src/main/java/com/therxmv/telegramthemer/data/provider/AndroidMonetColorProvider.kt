package com.therxmv.telegramthemer.data.provider

import android.content.Context
import androidx.core.content.ContextCompat
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.domain.provider.MonetColorProvider
import javax.inject.Inject

class AndroidMonetColorProvider @Inject constructor(
    private val context: Context,
) : MonetColorProvider {

    override fun getAccentColor(isDark: Boolean): Int {
        val colorRes = if (isDark) R.color.theme_accent1_200 else R.color.theme_accent1_500
        return ContextCompat.getColor(context, colorRes)
    }
}
