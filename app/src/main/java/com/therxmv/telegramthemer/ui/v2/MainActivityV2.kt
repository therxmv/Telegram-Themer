package com.therxmv.telegramthemer.ui.v2

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.therxmv.preview.AppbarColors
import com.therxmv.preview.ChatsColors
import com.therxmv.preview.PreviewColorsModel
import com.therxmv.preview.TabsColors
import com.therxmv.telegramthemer.databinding.ActivityMainV2Binding
import com.therxmv.telegramthemer.ui.base.BaseBindingActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityV2 : BaseBindingActivity<ActivityMainV2Binding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainV2Binding::inflate)

        val list = listOf(
            "#ff0000",
            "#00ff00",
            "#0000ff",
            "#f18900",
            "#f10580",
        )

        lifecycleScope.launch {
            while (true) {
                list.forEachIndexed { index, accent ->
                    delay(500)
                    val background = Color.parseColor(if (true) "#000000" else "#ffffff")
                    val accentColor = Color.parseColor(accent)
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
                    binding.chatListPreview1.setColors(colors)
                }
            }
        }

        // TODO set up toolbar
//        ColorPickerFragment.createInstance().show(supportFragmentManager, "ColorPickerFragment")
    }
}