package com.therxmv.telegramthemer.domain.usecase

import com.therxmv.telegramthemer.domain.source.TemplateRemoteSource
import javax.inject.Inject

class SyncTemplatesUseCase @Inject constructor(
    private val templateRemoteSource: TemplateRemoteSource,
) {
    suspend operator fun invoke() = templateRemoteSource.syncTemplates()
}
