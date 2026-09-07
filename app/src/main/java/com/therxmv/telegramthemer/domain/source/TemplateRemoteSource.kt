package com.therxmv.telegramthemer.domain.source

/**
 * Fetches the theme templates index and per-style template JSON files from
 * GitHub and caches them locally (see `TemplateLocalSource`), so
 * `ThemeValues` and `GetAvailableStylesUseCase` can pick up fixes/new styles
 * without an app update. Never throws - every failure (offline, timeout, a
 * malformed response) is handled internally by keeping whatever was already
 * cached/bundled.
 */
interface TemplateRemoteSource {
    suspend fun syncTemplates()
}
