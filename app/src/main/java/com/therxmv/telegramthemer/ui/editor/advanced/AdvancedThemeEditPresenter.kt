package com.therxmv.telegramthemer.ui.editor.advanced

import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.usecase.GetPreviewColorsModelUseCase
import com.therxmv.telegramthemer.ui.editor.ThemeEditorEvent
import com.therxmv.telegramthemer.ui.editor.ThemeEditorEventProvider
import com.therxmv.telegramthemer.ui.editor.ThemeStateListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class AdvancedThemeEditPresenter @Inject constructor(
    private val themeEditorEventProvider: ThemeEditorEventProvider,
    private val getPreviewColorsModel: GetPreviewColorsModelUseCase,
) : AdvancedThemeEditContract.Presenter(), ThemeStateListener {

    private var currentState: ThemeState? = null

    override fun attachView(view: AdvancedThemeEditContract.View, coroutineScope: CoroutineScope) {
        super.attachView(view, coroutineScope)

        themeEditorEventProvider.eventFlow.update {
            ThemeEditorEvent.SubscribeOnColorChanges(this@AdvancedThemeEditPresenter)
        }

        with(view) {
            setUpOnPreviewClick { key, color ->
                themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.OpenColorPicker(key, color) }
            }
            setUpResetButton {
                themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.ResetOverwrittenColors }
            }
        }
    }

    override fun detachView() {
        themeEditorEventProvider.eventFlow.update {
            ThemeEditorEvent.UnsubscribeFromColorChanges(this@AdvancedThemeEditPresenter)
        }
        currentState = null
        super.detachView()
    }

    override fun onStateChange(themeState: ThemeState) {
        if (themeState == currentState) return

        val model = getPreviewColorsModel(themeState)
        view.setPreviewColors(model)

        currentState = themeState
    }
}