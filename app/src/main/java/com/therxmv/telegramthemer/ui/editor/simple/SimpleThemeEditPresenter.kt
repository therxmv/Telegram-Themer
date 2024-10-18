package com.therxmv.telegramthemer.ui.editor.simple

import android.graphics.Color
import com.therxmv.preview.AppbarColors
import com.therxmv.preview.ChatsColors
import com.therxmv.preview.PreviewColorsModel
import com.therxmv.preview.TabsColors
import com.therxmv.telegramthemer.ui.editor.ThemeEditorEvent
import com.therxmv.telegramthemer.ui.editor.ThemeEditorEventProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SimpleThemeEditPresenter @Inject constructor(
    private val themeEditorEventProvider: ThemeEditorEventProvider,
): SimpleThemeEditContract.Presenter() {

    override fun attachView(view: SimpleThemeEditContract.View, coroutineScope: CoroutineScope) {
        super.attachView(view, coroutineScope)

        view.setUpColorPickerButton {
            themeEditorEventProvider.eventFlow.update {
                ThemeEditorEvent.OpenColorPicker(
                    onColorChange = ::onColorChanged,
                )
            }
        }
    }

    private fun onColorChanged(color: Int) {
        view.setColorPickerBackground(color)
        setThemeColors(color)
    }

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