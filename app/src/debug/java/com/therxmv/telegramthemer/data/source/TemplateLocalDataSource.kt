package com.therxmv.telegramthemer.data.source

import android.content.Context
import com.therxmv.telegramthemer.data.extensions.jsonToMap
import com.therxmv.telegramthemer.domain.model.DEFAULT_STYLE_ID
import com.therxmv.telegramthemer.domain.model.TemplateFiles
import com.therxmv.telegramthemer.domain.model.TemplatesIndex
import com.therxmv.telegramthemer.domain.source.TemplateLocalSource
import kotlinx.serialization.json.Json
import java.io.Reader
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Debug-build [TemplateLocalSource]: reads straight from bundled assets on
 * every call - no cache directory, no downloaded-content persistence, no
 * in-memory memoization - so editing a template JSON file under
 * `app/src/main/assets` and reinstalling is all that's needed to see a
 * change, with nothing cached from a previous run to clear first. Pairs
 * with the no-op [TemplateRemoteDataSource] in this same source set, so debug builds never
 * touch the network either. See the release counterpart (same package+class
 * path under `src/release/`) for the cached-download-or-asset behavior this
 * replaces, and its doc for how the source-set split works.
 */
@Singleton
class TemplateLocalDataSource @Inject constructor(
    private val context: Context,
) : TemplateLocalSource {

    /**
     * The bundled asset, or - if a style id doesn't resolve (a removed style,
     * or a legacy pre-migration [com.therxmv.telegramthemer.domain.model.ThemeState])
     * - the "default" style's file for the same platform/mode, which is
     * always bundled. Same safety net as the release implementation; this is
     * about asset resolution correctness, not caching.
     */
    override fun getTemplateMap(fileName: String): Map<String, String> =
        runCatching { openAsset(fileName).use { it.jsonToMap() } }
            .getOrElse { openAsset(defaultFallbackFileName(fileName)).use { it.jsonToMap() } }

    override fun getTemplatesIndex(): TemplatesIndex =
        runCatching {
            openAsset(TemplateFiles.TEMPLATES_INDEX).use { reader ->
                Json.decodeFromString<TemplatesIndex>(reader.readText())
            }
        }.getOrDefault(TemplatesIndex())

    override fun hasTemplate(fileName: String): Boolean =
        runCatching { context.assets.open(fileName).close() }.isSuccess

    // No-ops: nothing ever calls these, since this build's TemplateRemoteSource
    // never syncs - kept only to satisfy the interface.
    override fun saveTemplate(fileName: String, content: String) = Unit
    override fun getLastSyncedAt(): Long = 0L
    override fun markSynced() = Unit

    private fun openAsset(fileName: String): Reader = context.assets.open(fileName).bufferedReader()

    private fun defaultFallbackFileName(fileName: String): String {
        val platform = fileName.substringBefore("_")
        val modeWithExtension = fileName.substringAfterLast("_")
        return "${platform}_${DEFAULT_STYLE_ID}_$modeWithExtension"
    }
}
