package com.therxmv.telegramthemer.domain.source

import com.therxmv.telegramthemer.domain.model.TemplatesIndex

/**
 * Local (cached-download-or-bundled-asset) access to theme template files -
 * the full contract, implemented by [com.therxmv.telegramthemer.data.source.TemplateLocalDataSource]
 * and shared, via this interface, by both:
 *  - the read side: [com.therxmv.telegramthemer.data.values.AndroidThemeValuesProvider]/
 *    [com.therxmv.telegramthemer.data.values.IosThemeValuesProvider] (`getTemplateMap`) and
 *    [com.therxmv.telegramthemer.domain.usecase.GetAvailableStylesUseCase]/
 *    [com.therxmv.telegramthemer.domain.usecase.GetTemplateCapabilitiesUseCase]
 *    (`getTemplatesIndex`/`hasTemplate` - a remotely-pushed style isn't guaranteed to cover
 *    every platform/mode, so callers must check availability rather than assume it)
 *  - the write side: [com.therxmv.telegramthemer.data.source.TemplateRemoteDataSource], which
 *    persists a successful download (`saveTemplate`) and the 1-day sync TTL (`getLastSyncedAt`/`markSynced`)
 *
 * Same layering [com.therxmv.telegramthemer.domain.usecase.GetCachedThemeUseCase] follows via
 * [SharedPrefsSource] - depend on this interface, never on the concrete data-source class.
 */
interface TemplateLocalSource {
    fun getTemplateMap(fileName: String): Map<String, String>
    fun getTemplatesIndex(): TemplatesIndex

    /** Cached-or-bundled existence check, with no fallback to another file - unlike [getTemplateMap]. */
    fun hasTemplate(fileName: String): Boolean

    fun saveTemplate(fileName: String, content: String)
    fun getLastSyncedAt(): Long
    fun markSynced()
}
