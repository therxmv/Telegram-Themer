package com.therxmv.telegramthemer.ui.editor.simple

import android.graphics.Color
import com.therxmv.preview.AppbarColors
import com.therxmv.preview.ChatsColors
import com.therxmv.preview.PreviewColorsModel
import com.therxmv.preview.TabsColors
import com.therxmv.telegramthemer.ui.editor.ColorChangeListener
import com.therxmv.telegramthemer.ui.editor.ThemeEditorEvent
import com.therxmv.telegramthemer.ui.editor.ThemeEditorEventProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SimpleThemeEditPresenter @Inject constructor(
    private val themeEditorEventProvider: ThemeEditorEventProvider,
): SimpleThemeEditContract.Presenter(), ColorChangeListener {

    override fun attachView(view: SimpleThemeEditContract.View, coroutineScope: CoroutineScope) {
        super.attachView(view, coroutineScope)

        themeEditorEventProvider.eventFlow.update {
            ThemeEditorEvent.SubscribeOnColorChanges(this@SimpleThemeEditPresenter)
        }

        view.setUpColorPickerButton {
            themeEditorEventProvider.eventFlow.update { ThemeEditorEvent.OpenColorPicker }
        }
    }

    override fun detachView() {
        themeEditorEventProvider.eventFlow.update {
            ThemeEditorEvent.UnsubscribeFromColorChanges(this@SimpleThemeEditPresenter)
        }
        super.detachView()
    }

    override fun onColorChange(color: Int) { // TODO change button border and theme background
        view.setColorPickerBackground(color)
        setThemeColors(color)
    }

    /* TODO
        Make map with key as accent, background, etc and value as value to which it should apply
        mapOf(
            "background" to listOf("actionBarActionModeDefault"),
            "accent_500" to listOf("actionButton", "appbarTitle"),
        )
        mapOf(
            "background" to {color hex or int},
            "accent_500" to {color hex or int},
        )
     */

    private fun setThemeColors(color: Int) {
        val background = Color.parseColor("#000000")
        val accentColor = color
        val grey = Color.parseColor("#7a7a7a")

        val colors = PreviewColorsModel(
            accent = accentColor,
            background = background,
            actionButton = accentColor,
            appbarColors = AppbarColors(
                appbarIcon = grey,
                appbarTitle = accentColor,
            ),
            tabsColors = TabsColors(
                tab = grey,
                selectedTab = accentColor,
                tabSelector = accentColor,
                tabUnread = accentColor,
            ),
            chatsColors = ChatsColors(
                background = background,
                chatDate = grey,
                unreadCounter = accentColor,
                unreadCounterMuted = grey,
                avatarColor = accentColor,
                chatName = grey,
                senderName = accentColor,
                message = grey,
                actionMessage = accentColor,
                muteIcon = grey,
                online = accentColor,
                secretIcon = accentColor,
                secretName = accentColor,
                sentCheck = accentColor,
            ),
        )

        view.setThemeColors(colors)
    }
}