package com.therxmv.telegramthemer.data.source

import android.content.Context
import com.therxmv.telegramthemer.data.extensions.jsonToMap
import com.therxmv.telegramthemer.domain.model.DEFAULT_STYLE_ID
import com.therxmv.telegramthemer.domain.model.TemplateFiles
import com.therxmv.telegramthemer.domain.model.TemplatesIndex
import com.therxmv.telegramthemer.domain.source.TemplateLocalSource
import kotlinx.serialization.json.Json
import java.io.File
import java.io.Reader
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Shared read/write access to theme template files: cached-download-or-bundled-asset
 * reads for [AndroidThemeValuesProvider][com.therxmv.telegramthemer.data.values.AndroidThemeValuesProvider]/
 * [IosThemeValuesProvider][com.therxmv.telegramthemer.data.values.IosThemeValuesProvider]
 * and [GetAvailableStylesUseCase][com.therxmv.telegramthemer.domain.usecase.GetAvailableStylesUseCase],
 * plus the writes [TemplateRemoteDataSource] persists after a successful
 * download. Must stay a singleton (both the class annotation and its `@Binds`
 * in `TemplateModule`) since its in-memory caches are shared by all of them.
 */
@Singleton
class TemplateLocalDataSource @Inject constructor(
    private val context: Context,
) : TemplateLocalSource {

    companion object {
        private const val CACHE_DIR_NAME = "theme_templates"
        private const val LAST_SYNC_FILE = ".last_sync"
        private const val TMP_SUFFIX = ".tmp"
    }

    private val cacheDir by lazy { File(context.filesDir, CACHE_DIR_NAME).apply { mkdirs() } }
    private val syncMarkerFile by lazy { File(cacheDir, LAST_SYNC_FILE) }

    private val templateMapCache = ConcurrentHashMap<String, Map<String, String>>()
    @Volatile private var templatesIndexCache: TemplatesIndex? = null

    /**
     * Cached file if one was downloaded, else the bundled asset. If neither
     * exists (a style id that's no longer valid - a removed remote style, or
     * a legacy pre-migration cached [com.therxmv.telegramthemer.domain.model.ThemeState]
     * whose id somehow still doesn't resolve), retries once against the
     * "default" style's file for the same platform/mode, which is always bundled.
     */
    override fun getTemplateMap(fileName: String): Map<String, String> =
        templateMapCache.getOrPut(fileName) {
            runCatching { openTemplate(fileName).use { it.jsonToMap() } }
                .getOrElse { openTemplate(defaultFallbackFileName(fileName)).use { it.jsonToMap() } }
        }

    override fun getTemplatesIndex(): TemplatesIndex =
        templatesIndexCache ?: readTemplatesIndex().also { templatesIndexCache = it }

    override fun hasTemplate(fileName: String): Boolean =
        File(cacheDir, fileName).exists() || runCatching { context.assets.open(fileName).close() }.isSuccess

    /**
     * Writes to a temp file then renames over the real one (atomic - avoids a
     * half-written file if the process dies mid-download), then evicts the
     * relevant in-memory cache entry so the next read picks up the fresh copy.
     */
    override fun saveTemplate(fileName: String, content: String) {
        val target = File(cacheDir, fileName)
        val tmp = File(cacheDir, "$fileName$TMP_SUFFIX")
        tmp.writeText(content)
        tmp.renameTo(target)

        templateMapCache.remove(fileName)
        if (fileName == TemplateFiles.TEMPLATES_INDEX) templatesIndexCache = null
    }

    override fun getLastSyncedAt(): Long =
        runCatching { syncMarkerFile.readText().trim().toLong() }.getOrDefault(0L)

    override fun markSynced() {
        syncMarkerFile.writeText(System.currentTimeMillis().toString())
    }

    private fun readTemplatesIndex(): TemplatesIndex =
        runCatching {
            openTemplate(TemplateFiles.TEMPLATES_INDEX).use { reader ->
                Json.decodeFromString<TemplatesIndex>(reader.readText())
            }
        }.getOrDefault(TemplatesIndex())

    private fun openTemplate(fileName: String): Reader {
        val cached = File(cacheDir, fileName)
        return if (cached.exists()) cached.bufferedReader()
        else context.assets.open(fileName).bufferedReader()
    }

    private fun defaultFallbackFileName(fileName: String): String {
        val platform = fileName.substringBefore("_")
        val modeWithExtension = fileName.substringAfterLast("_")
        return "${platform}_${DEFAULT_STYLE_ID}_$modeWithExtension"
    }
}
