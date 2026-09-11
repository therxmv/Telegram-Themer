package com.therxmv.telegramthemer.data.source

import com.therxmv.telegramthemer.domain.source.TemplateRemoteSource
import javax.inject.Inject

/**
 * Debug-build [TemplateRemoteSource]: does nothing - no network calls, no
 * template sync, so debug builds run entirely off bundled assets via the
 * matching debug-only [TemplateLocalDataSource]. The release implementation
 * (the actual GitHub-fetching one) lives at the same package+class path
 * under `src/release/`; see its doc for how the source-set split works.
 */
class TemplateRemoteDataSource @Inject constructor() : TemplateRemoteSource {
    override suspend fun syncTemplates() = Unit
}
