package com.therxmv.telegramthemer.domain.usecase

import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.telegramthemer.domain.adapter.PreviewColorsAdapter
import com.therxmv.telegramthemer.domain.model.ThemeState
import javax.inject.Inject

class GetPreviewColorsModelUseCase @Inject constructor(
    private val previewColorsAdapter: PreviewColorsAdapter,
) {

    operator fun invoke(themeState: ThemeState): PreviewColorsModel =
        previewColorsAdapter.getThemePreviewColors(themeState)
}