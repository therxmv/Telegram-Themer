package com.therxmv.telegramthemer.ui.editor

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.therxmv.telegramthemer.databinding.ActivityThemeEditorBinding
import com.therxmv.telegramthemer.ui.base.BaseBindingActivity
import com.therxmv.telegramthemer.ui.editor.picker.ColorPickerFragment
import javax.inject.Inject

class ThemeEditorActivity : BaseBindingActivity<ActivityThemeEditorBinding>(),
    ThemeEditorContract.View {

    @Inject
    lateinit var presenter: ThemeEditorContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityThemeEditorBinding::inflate)
        presenter.attachView(this@ThemeEditorActivity, lifecycleScope)

//        val list = listOf(
//            "#ff0000",
//            "#00ff00",
//            "#0000ff",
//            "#f18900",
//            "#f10580",
//        )
//
//        lifecycleScope.launch {
//            while (true) {
//                list.forEachIndexed { index, accent ->
//                    delay(500)
//                    val background = Color.parseColor(if (true) "#000000" else "#ffffff")
//                    val accentColor = Color.parseColor(accent)
//                    val grey = Color.parseColor("#7a7a7a")
//
//                    val colors = PreviewColorsModel(
//                        accent = accentColor,
//                        background = background,
//                        actionButton = accentColor,
//                        appbarColors = AppbarColors(
//                            appbarIcon = grey,
//                            appbarTitle = accentColor,
//                        ),
//                        tabsColors = TabsColors(
//                            tab = grey,
//                            selectedTab = accentColor,
//                            tabSelector = accentColor,
//                            tabUnread = accentColor,
//                        ),
//                        chatsColors = ChatsColors(
//                            background = background,
//                            chatDate = grey,
//                            unreadCounter = accentColor,
//                            unreadCounterMuted = grey,
//                            avatarColor = accentColor,
//                            chatName = grey,
//                            senderName = accentColor,
//                            message = grey,
//                            actionMessage = accentColor,
//                            muteIcon = grey,
//                            online = accentColor,
//                            secretIcon = accentColor,
//                            secretName = accentColor,
//                            sentCheck = accentColor,
//                        ),
//                    )
//                    binding.chatListPreview1.setColors(colors)
//                }
//            }
//        }

        // TODO set up toolbar
//        ColorPickerFragment.createInstance().show(supportFragmentManager, "ColorPickerFragment")
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun openColorPicker() {
        ColorPickerFragment.createInstance().show(supportFragmentManager, "ColorPickerFragment")
    }
}