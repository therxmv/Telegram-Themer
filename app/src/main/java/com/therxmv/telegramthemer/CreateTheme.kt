package com.therxmv.telegramthemer

fun createTheme(file: String, accent: String): String {
    val tints: Map<String, String> = getColorTints(accent)

    val hexesNames = mapOf(
        "bl_900" to "#000000",
        "wh_100" to "#FFFFFF",
        "gr_200" to "#F0F0F0",
        "gr_300" to "#DBDBDB",
        "gr_500" to "#919191",
        "gr_700" to "#464646",
        "ac_200" to tints["ac_200"],//"#E0D4FF",
        "ac_300" to tints["ac_300"],//"#8D65F3",
        "ac_500" to tints["ac_500"],//"#713FF1",
        "ac_700" to tints["ac_700"],//"#5A32C0",
        "ac_800" to tints["ac_800"],//"#432590",
        "yl_700" to "#DFA000",
        "rd_300" to "#FF6666",
        "rd_500" to "#DE4747",
        "rd_700" to "#C64949",
        "gn_300" to "#9ED448",
        "gn_500" to "#50B232",
        "gn_700" to "#26972C",
        "pu_500" to "#8E85EE",
        "tr_500" to "#00000000",
        "tr_ac300" to "#44${tints["ac_300"]?.drop(1)}",
        "tr_ac500" to "#77${tints["ac_500"]?.drop(1)}",
        "tr_gr500" to "#7B919191",
    )

    var theme = file;

    hexesNames.forEach{
        it.value?.let { it1 -> theme = theme.replace(it.key, it1) }
    }

    return theme;
}