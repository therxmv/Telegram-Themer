package com.therxmv.telegramthemer.domain.usecase

import com.therxmv.telegramthemer.domain.provider.MonetColorProvider
import javax.inject.Inject

class GetMonetAccentColorUseCase @Inject constructor(
    private val monetColorProvider: MonetColorProvider,
) {

    operator fun invoke(isDark: Boolean): Int = monetColorProvider.getAccentColor(isDark)
}
