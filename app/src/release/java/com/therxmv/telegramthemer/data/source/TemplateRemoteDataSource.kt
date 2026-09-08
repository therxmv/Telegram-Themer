package com.therxmv.telegramthemer.data.source

import com.therxmv.telegramthemer.data.extensions.jsonToMap
import com.therxmv.telegramthemer.domain.model.Platform
import com.therxmv.telegramthemer.domain.model.TemplateFiles
import com.therxmv.telegramthemer.domain.model.TemplatesIndex
import com.therxmv.telegramthemer.domain.source.TemplateLocalSource
import com.therxmv.telegramthemer.domain.source.TemplateRemoteSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

/**
 * Release-build [TemplateRemoteSource]: downloads `templates_index.json`
 * and, per the styles it lists, every `{platform}_{styleId}_{light|dark}.json`
 * template from GitHub, validating and caching each independently via
 * [TemplateLocalSource]. See `TemplateRemoteSource` for the never-throws
 * contract this fulfils.
 *
 * Lives under `src/release/` on purpose - the debug build uses a no-op
 * counterpart at the same package+class path under `src/debug/`, so debug
 * builds never hit the network; see [TemplateLocalDataSource]'s doc for how
 * the source-set split works.
 */
class TemplateRemoteDataSource @Inject constructor(
    private val okHttpClient: OkHttpClient,
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    private val templateLocalSource: TemplateLocalSource,
) : TemplateRemoteSource {

    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/therxmv/Telegram-Themer/main/app/src/main/assets/"
        private val SYNC_TTL_MILLIS = TimeUnit.DAYS.toMillis(1)
    }

    override suspend fun syncTemplates() = withContext(ioDispatcher) {
        val elapsedSinceLastSync = System.currentTimeMillis() - templateLocalSource.getLastSyncedAt()
        if (elapsedSinceLastSync < SYNC_TTL_MILLIS) return@withContext

        // Fetch the index first so the template file list below reflects any
        // just-downloaded styles; a failure here just keeps the existing
        // cached/bundled index (and its template files) as-is.
        runCatching { fetchAndCacheIndex() }

        val index = templateLocalSource.getTemplatesIndex()
        val fileNames = index.styles.flatMap { style ->
            Platform.entries.flatMap { platform ->
                listOf(
                    TemplateFiles.fileName(platform, style.id, isDark = false),
                    TemplateFiles.fileName(platform, style.id, isDark = true),
                )
            }
        }

        coroutineScope {
            fileNames.map { fileName ->
                async { runCatching { fetchAndCacheTemplate(fileName) } }
            }.awaitAll()
        }

        // Recorded regardless of individual file outcomes, so one bad file
        // doesn't force a full retry storm on every subsequent app open.
        templateLocalSource.markSynced()
    }

    private fun fetchAndCacheIndex() {
        val body = fetchBody(TemplateFiles.TEMPLATES_INDEX) ?: return
        val index = Json.decodeFromString<TemplatesIndex>(body)
        if (index.styles.isEmpty()) return

        templateLocalSource.saveTemplate(TemplateFiles.TEMPLATES_INDEX, body)
    }

    private fun fetchAndCacheTemplate(fileName: String) {
        val body = fetchBody(fileName) ?: return
        val map = body.reader().jsonToMap()
        if (map.isEmpty()) return

        templateLocalSource.saveTemplate(fileName, body)
    }

    private fun fetchBody(fileName: String): String? {
        val request = Request.Builder().url(BASE_URL + fileName).build()
        okHttpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null
            return response.body?.string()?.takeIf { it.isNotBlank() }
        }
    }
}
