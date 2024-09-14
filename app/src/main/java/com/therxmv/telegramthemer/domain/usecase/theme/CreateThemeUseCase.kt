package com.therxmv.telegramthemer.domain.usecase.theme

import android.content.Context
import androidx.core.content.ContextCompat
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.domain.model.ThemeModel
import com.therxmv.telegramthemer.utils.ThemeAccent.ACCENT_200
import com.therxmv.telegramthemer.utils.ThemeAccent.ACCENT_300
import com.therxmv.telegramthemer.utils.ThemeAccent.ACCENT_500
import com.therxmv.telegramthemer.utils.ThemeAccent.ACCENT_700
import com.therxmv.telegramthemer.utils.ThemeAccent.ACCENT_800
import com.therxmv.telegramthemer.utils.checkVersionForMonet

class CreateThemeUseCase {

    operator fun invoke(
        context: Context,
        templateFile: String,
        tints: Map<String, String>,
        themeModel: ThemeModel,
    ): String {
        val black = if (themeModel.getMonetAvailability()) {
            getMonetHexColor(context, R.color.theme_neutral1_900)
        } else {
            if (themeModel.isAmoled) "#000000" else "#181818"
        }

        val white = if (themeModel.getMonetAvailability()) {
            getMonetHexColor(context, R.color.theme_neutral1_50)
        } else {
            "#FFFFFF"
        }

        val gr200 = if (
            themeModel.getMonetAvailability()
            && themeModel.isDark.not()
            && themeModel.isDefault
        ) {
            getMonetHexColor(context, R.color.theme_neutral1_100)
        } else {
            "#F9F9F9"
        }

        val gr900 = if (
            themeModel.getMonetAvailability()
            && themeModel.isDark
            && themeModel.isDefault
        ) {
            getMonetHexColor(context, R.color.theme_neutral1_800)
        } else {
            "#202020"
        }

        // TODO extract hardcoded colors
        val hexesNames = mapOf(
            "bl_900" to black,
            "wh_100" to white,
            "gr_200" to gr200,
            "gr_300" to "#DBDBDB",
            "gr_500" to "#919191",
            "gr_700" to "#707070",
            "gr_800" to "#464646",
            "gr_900" to gr900,
            "ac_200" to tints[ACCENT_200],
            "ac_300" to tints[ACCENT_300],
            "ac_500" to tints[ACCENT_500],
            "ac_700" to tints[ACCENT_700],
            "ac_800" to tints[ACCENT_800],
            "yl_500" to "#E3B727",
            "yl_700" to "#DFA000",
            "rd_300" to "#FF6666",
            "rd_500" to "#DE4747",
            "rd_700" to "#C64949",
            "gn_300" to "#9ED448",
            "gn_500" to "#50B232",
            "gn_700" to "#26972C",
            "pu_500" to "#8E85EE",
            "tr_500" to "#00000000",
            "tr_ac300" to "#44${tints[ACCENT_300]?.drop(1)}",
            "tr_ac500" to "#77${tints[ACCENT_500]?.drop(1)}",
            "tr_gr300" to "#77DBDBDB",
            "tr_gr500" to "#77919191",
            "tr_gr700" to "#AA707070",
            "tr_gr800" to "#AA464646",
        )

        return templateFile.replaceKeys(hexesNames, themeModel.isGradient)
    }

    private fun String.replaceKeys(hexes: Map<String, String?>, isGradient: Boolean): String {
        var theme = this

        hexes.forEach { hex ->
            hex.value?.let { theme = theme.replace(hex.key, it) }
        }

        if (isGradient.not()) {
            theme = theme.replace("chat_outBubbleGradient", "NoGradient")
        }

        return theme
    }

    private fun getMonetHexColor(context: Context, colorInt: Int): String {
        val color = ContextCompat.getColor(context, colorInt)
        return String.format("#%06X", 0xFFFFFF and color)
    }

    private fun ThemeModel.getMonetAvailability() = this.isMonetBackground && checkVersionForMonet()
}