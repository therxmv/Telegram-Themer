package com.therxmv.telegramthemer.ui.editor.simple

import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.usecase.GetPreviewColorsModelUseCase
import com.therxmv.telegramthemer.ui.editor.ThemeEditorEvent
import com.therxmv.telegramthemer.ui.editor.ThemeEditorEventProvider
import com.therxmv.telegramthemer.ui.editor.ThemeStateListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class SimpleThemeEditPresenter @Inject constructor(
    private val themeEditorEventProvider: ThemeEditorEventProvider,
    private val getPreviewColorsModel: GetPreviewColorsModelUseCase,
    @Named("Main") private val mainDispatcher: CoroutineDispatcher,
) : SimpleThemeEditContract.Presenter(), ThemeStateListener {

    private var currentState: ThemeState? = null
    private var previewAnimationJob: Job? = null

    override fun attachView(view: SimpleThemeEditContract.View, coroutineScope: CoroutineScope) {
        super.attachView(view, coroutineScope)

        themeEditorEventProvider.eventFlow.update {
            ThemeEditorEvent.SubscribeOnColorChanges(this@SimpleThemeEditPresenter)
        }

        with(view) {
            setUpColorPickerButton {
                themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.OpenColorPicker() }
            }
            setUpMoreOptionsButton {
                themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.OpenMoreOptions }
            }
            setUpExportButton {
                themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.ExportTheme }
            }
        }
    }

    override fun detachView() {
        themeEditorEventProvider.eventFlow.update {
            ThemeEditorEvent.UnsubscribeFromColorChanges(this@SimpleThemeEditPresenter)
        }
        currentState = null
        super.detachView()
    }

    override fun onStateChange(themeState: ThemeState) {
        if (themeState == currentState) return

        val model = getPreviewColorsModel(themeState)

        if (currentState == null // Animate on start
            || themeState.isMonet // On monet checkbox
            || currentState?.accent == themeState.accent // On other checkboxes
        ) {
            animatePreviewBackground(model.previewGradient)
        }

        view.setColorPickerColors(model.accent, model.background)
        view.setPreviewColors(model)

        currentState = themeState
    }

    private fun animatePreviewBackground(gradient: List<Int>) {
        previewAnimationJob?.cancel()
        previewAnimationJob = coroutineScope.launch(mainDispatcher) {
            view.startPreviewAnimation(gradient.toIntArray())
        }
    }
}