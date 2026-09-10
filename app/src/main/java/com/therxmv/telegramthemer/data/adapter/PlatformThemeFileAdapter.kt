package com.therxmv.telegramthemer.data.adapter

import com.therxmv.telegramthemer.domain.adapter.ThemeFileAdapter
import com.therxmv.telegramthemer.domain.model.Platform
import com.therxmv.telegramthemer.domain.model.ThemeState
import java.io.File
import javax.inject.Inject

/**
 * Routes theme file generation to the adapter matching [ThemeState.platform] —
 * [AndroidThemeFileAdapter] for `.attheme`, [IosThemeFileAdapter] for
 * `.tgios-theme`. This is the [ThemeFileAdapter] bound in DI; the two
 * platform adapters are plain, unbound implementations only reached through
 * this dispatcher.
 */
class PlatformThemeFileAdapter @Inject constructor(
    private val androidAdapter: AndroidThemeFileAdapter,
    private val iosAdapter: IosThemeFileAdapter,
) : ThemeFileAdapter {

    override fun createThemeFile(themeState: ThemeState): File =
        when (themeState.platform) {
            Platform.ANDROID -> androidAdapter.createThemeFile(themeState)
            Platform.IOS -> iosAdapter.createThemeFile(themeState)
        }
}
