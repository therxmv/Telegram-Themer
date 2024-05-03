package com.therxmv.telegramthemer.domain.usecase

import androidx.annotation.StringRes
import com.therxmv.telegramthemer.R

class IsValidInputUseCase {

    operator fun invoke(input: String): InvalidState? =
        when {
            input.isEmpty() -> InvalidState.Empty
            input.length != 6 -> InvalidState.InvalidLength
            input.toIntOrNull(16) == null -> InvalidState.InvalidNumber
            else -> null
        }
}

sealed class InvalidState(@StringRes val text: Int) {
    object Empty : InvalidState(R.string.empty_error)
    object InvalidLength : InvalidState(R.string.length_error)
    object InvalidNumber : InvalidState(R.string.invalid_error)
}