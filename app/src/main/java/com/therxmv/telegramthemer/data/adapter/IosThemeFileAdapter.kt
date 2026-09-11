package com.therxmv.telegramthemer.data.adapter

import android.content.Context
import com.therxmv.telegramthemer.data.adapter.IosThemeFileAdapter.Companion.LITERAL_KEYS
import com.therxmv.telegramthemer.data.extensions.colorToHex
import com.therxmv.telegramthemer.data.values.IosThemeValuesProvider
import com.therxmv.telegramthemer.domain.adapter.ThemeFileAdapter
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.model.TintedThemeColors
import com.therxmv.telegramthemer.domain.model.transparent
import com.therxmv.telegramthemer.domain.values.ThemeColors
import java.io.File
import javax.inject.Inject
import kotlin.random.Random

/**
 * Uses tints from [ThemeColors] and the template map from [IosThemeValuesProvider]
 * (injected concretely, not through the
 * [com.therxmv.telegramthemer.domain.values.ThemeValues] interface, so this
 * always resolves iOS regardless of [ThemeState.platform]) to build a nested,
 * indentation-scoped `.tgios-theme` document (Telegram-for-iOS's theme
 * format), the iOS counterpart to [AndroidThemeFileAdapter]'s `.attheme`
 * output. See the `ios-theme-key` project skill for the dot-path template
 * convention and the role → format rules below.
 */
class IosThemeFileAdapter @Inject constructor(
    private val context: Context,
    private val themeColors: ThemeColors,
    private val iosThemeValues: IosThemeValuesProvider,
) : ThemeFileAdapter {

    companion object {
        private const val DARK_LABEL = "dark"
        private const val LIGHT_LABEL = "light"
        private const val MONET_LABEL = "monet"
        private const val EXTENSION = ".tgios-theme"
        private const val INDENT = "  "

        // Keys whose value is a literal JSON value (bool/string), not a role name.
        private val LITERAL_KEYS = setOf(
            "dark",
            "basedOn",
            "root.keyboard",
            "intro.statusBar",
            "root.statusBar",
            "actionSheet.bgType",
            "notification.expanded.bgType",
            "chat.animateMessageColors",
        )
    }

    override fun createThemeFile(themeState: ThemeState): File {
        val tints = themeColors.getTintedColorSchema(themeState)
        val templateMap = iosThemeValues.getTemplateMap(themeState)

        val resolvedValues = templateMap.mapValues { (dotPath, roleOrLiteral) ->
            resolveValue(dotPath, roleOrLiteral, tints)
        }

        val content = buildString {
            appendLine("name: ${getBaseName(themeState)}")
            appendNested(buildNestedTree(resolvedValues), indent = 0)
        }

        val file = File(context.filesDir, getFileName(themeState))
        file.writeText(content)

        return file
    }

    /**
     * Resolves one template entry to its final output token: the literal
     * value as-is for [LITERAL_KEYS], otherwise a role lookup.
     *
     * // TODO: single-element overrides ([ThemeState.overwrittenColors]) aren't
     *  applied here. They're recorded keyed by Android `.attheme` element
     *  names (see [com.therxmv.preview.utils.AtthemePreviewKeys.similarKeys]),
     *  which don't correspond to iOS's dot-path template keys - looking them
     *  up the same way [AndroidThemeFileAdapter] does only silently works for
     *  the couple of keys whose override happens to be recorded under a
     *  plain role name (tt_background/tt_onBackground) and silently drops
     *  every other override, which is worse than just not supporting it.
     *  Needs a real per-element override scheme shared across platforms
     *  (or an iOS-specific preview/override key set) before this can work.
     */
    private fun resolveValue(dotPath: String, roleOrLiteral: String, tints: TintedThemeColors): String {
        if (dotPath in LITERAL_KEYS) return roleOrLiteral

        if (roleOrLiteral == transparent(0)) return "clear"

        // Already the correct digit count (6 for plain roles, 8/AARRGGBB for tr_* roles)
        // since that's how the raw values are constructed in getTintedColorSchema().
        return tints.rawHex(roleOrLiteral)?.removePrefix("#")?.lowercase() ?: "000000"
    }

    /**
     * Splits each dot-path key into its nested form. Since Gson preserves
     * JSON object key order and the source templates already declare keys
     * grouped by section, walking [flatValues] in order and inserting into
     * the tree reproduces the correct grouped, indented structure with no
     * sorting needed.
     */
    private fun buildNestedTree(flatValues: Map<String, String>): Map<String, Any> {
        val root = LinkedHashMap<String, Any>()
        flatValues.forEach { (dotPath, value) ->
            var current = root
            val segments = dotPath.split(".")
            segments.dropLast(1).forEach { segment ->
                @Suppress("UNCHECKED_CAST")
                current = current.getOrPut(segment) { LinkedHashMap<String, Any>() } as LinkedHashMap<String, Any>
            }
            current[segments.last()] = value
        }
        return root
    }

    private fun StringBuilder.appendNested(node: Map<String, Any>, indent: Int) {
        node.forEach { (key, value) ->
            when (value) {
                is String -> appendLine("${INDENT.repeat(indent)}$key: $value")
                else -> {
                    appendLine("${INDENT.repeat(indent)}$key:")
                    @Suppress("UNCHECKED_CAST")
                    appendNested(value as Map<String, Any>, indent + 1)
                }
            }
        }
    }

    private fun getBaseName(state: ThemeState): String {
        val style = state.style
        val dark = DARK_LABEL.takeIf { state.isDark } ?: LIGHT_LABEL
        val color = MONET_LABEL.takeIf { state.isMonet } ?: state.accent.colorToHex().drop(1)

        return "$style-$dark-$color"
    }

    private fun getFileName(state: ThemeState): String {
        val uniqueId = Random.nextInt(100, 999)

        return "${getBaseName(state)}-$uniqueId$EXTENSION"
    }
}
