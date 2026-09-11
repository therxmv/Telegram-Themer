package com.therxmv.telegramthemer.domain.usecase

import com.therxmv.telegramthemer.domain.source.SharedPrefsSource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ShouldRequestReviewUseCase @Inject constructor(
    private val prefsSource: SharedPrefsSource,
) {

    companion object {
        private val REQUEST_COOLDOWN_MILLIS = TimeUnit.DAYS.toMillis(14)
    }

    operator fun invoke(): Boolean {
        val now = System.currentTimeMillis()
        val lastRequestedAt = prefsSource.getLastReviewRequestTimestamp()

        if (lastRequestedAt == 0L) {
            prefsSource.saveLastReviewRequestTimestamp(now)
            return false
        }
        if (now - lastRequestedAt < REQUEST_COOLDOWN_MILLIS) return false

        prefsSource.saveLastReviewRequestTimestamp(now)
        return true
    }
}
