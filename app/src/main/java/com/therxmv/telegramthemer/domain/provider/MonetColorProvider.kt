package com.therxmv.telegramthemer.domain.provider

/**
 * Resolves Android 12+'s Material You ("Monet") system accent color - a
 * device/wallpaper-derived color the OS provides, independent of anything
 * the user picked. See `GetMonetAccentColorUseCase` and the Android
 * implementation (`AndroidMonetColorProvider`) for where platform Resources
 * actually get touched - presenters only ever see the resolved [Int].
 */
interface MonetColorProvider {
    fun getAccentColor(isDark: Boolean): Int
}
