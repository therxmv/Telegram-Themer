package com.therxmv.telegramthemer.ui.editor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Storage that holds a mutable [Flow] of [ThemeEditorEvent]s.
 * Those events are produced and emitted by Editor Fragment's Presenters
 * and intended to be handled (reacted to) by the parent activity.
 */
class ThemeEditorEventStore @Inject constructor() : ThemeEditorEventProvider {
    override val eventFlow = MutableStateFlow<ThemeEditorEvent?>(null)
}

/**
 * Read-only provider
 */
interface ThemeEditorEventProvider {
    val eventFlow: MutableStateFlow<ThemeEditorEvent?>
}

sealed interface ThemeEditorEvent {

    data object OpenColorPicker : ThemeEditorEvent
    data class SubscribeOnColorChanges(val listener: ColorChangeListener) : ThemeEditorEvent
    data class UnsubscribeFromColorChanges(val listener: ColorChangeListener) : ThemeEditorEvent
}