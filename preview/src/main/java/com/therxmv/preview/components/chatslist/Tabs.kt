package com.therxmv.preview.components.chatslist

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.therxmv.preview.DpValues
import com.therxmv.preview.common.preview.ClickablePreview
import com.therxmv.preview.common.preview.ColorfulPreview
import com.therxmv.preview.common.view.CircleView
import com.therxmv.preview.common.view.ColorfulView
import com.therxmv.preview.common.view.RoundedRectangleView
import com.therxmv.preview.model.TabsColors
import com.therxmv.preview.utils.AtthemePreviewKeys
import com.therxmv.preview.utils.AtthemePreviewKeys.actionBarTabActiveText
import com.therxmv.preview.utils.AtthemePreviewKeys.actionBarTabLine
import com.therxmv.preview.utils.AtthemePreviewKeys.actionBarTabUnactiveText
import com.therxmv.preview.utils.AtthemePreviewKeys.chats_tabUnreadUnactiveBackground

class Tabs(
    dpValues: DpValues,
    context: Context,
    attr: AttributeSet? = null,
) : RelativeLayout(context, attr),
    ColorfulPreview<TabsColors>,
    ClickablePreview {

    constructor(context: Context, attr: AttributeSet? = null) : this(DpValues(context), context, attr)

    private val tabWidth = dpValues.dp50
    private val tabHeight = dpValues.dp14
    private val margin = dpValues.dp4

    private val firstTab = TabModel(
        id = generateViewId(),
        unreadCounterId = generateViewId(),
        selectorId = generateViewId(),
    )
    private val middleTab = TabModel(
        id = generateViewId(),
        unreadCounterId = null,
        selectorId = null,
    )
    private val lastTab = TabModel(
        id = generateViewId(),
        unreadCounterId = generateViewId(),
        selectorId = null,
    )

    init {
        createTabs()
    }

    private fun createTabs() { // TODO refactor to generate them like messages
        RoundedRectangleView.create(
            context = context,
            id = firstTab.id,
            width = tabWidth,
            height = tabHeight,
            setUpLayoutParams = {
                addRule(ALIGN_PARENT_TOP)
                addRule(ALIGN_PARENT_START)
            }
        ).also { addView(it) }

        RoundedRectangleView.create(
            context = context,
            id = requireNotNull(firstTab.selectorId),
            width = tabHeight + tabWidth + margin,
            height = margin,
            setUpLayoutParams = {
                topMargin = margin
                addRule(BELOW, firstTab.id)
            }
        ).also { addView(it) }

        CircleView.create(
            context = context,
            id = requireNotNull(firstTab.unreadCounterId),
            width = tabHeight,
            height = tabHeight,
            setUpLayoutParams = {
                marginStart = margin
                addRule(END_OF, firstTab.id)
            }
        ).also { addView(it) }

        RoundedRectangleView.create(
            context = context,
            id = middleTab.id,
            width = tabWidth,
            height = tabHeight,
            setUpLayoutParams = {
                addRule(CENTER_HORIZONTAL)
            }
        ).also { addView(it) }

        RoundedRectangleView.create(
            context = context,
            id = lastTab.id,
            width = tabWidth,
            height = tabHeight,
            setUpLayoutParams = {
                marginEnd = margin
                addRule(START_OF, requireNotNull(lastTab.unreadCounterId))
            }
        ).also { addView(it) }

        CircleView.create(
            context = context,
            id = requireNotNull(lastTab.unreadCounterId),
            width = tabHeight,
            height = tabHeight,
            setUpLayoutParams = {
                addRule(ALIGN_PARENT_END)
            }
        ).also { addView(it) }
    }

    override fun setColors(colors: TabsColors) {
        findViewById<ColorfulView>(firstTab.id)?.setColor(colors.selectedTab)
        findViewById<ColorfulView>(requireNotNull(firstTab.selectorId))?.setColor(colors.tabSelector)
        findViewById<ColorfulView>(requireNotNull(firstTab.unreadCounterId))?.setColor(colors.tabUnread)

        findViewById<ColorfulView>(middleTab.id)?.setColor(colors.tab)

        findViewById<ColorfulView>(lastTab.id)?.setColor(colors.tab)
        findViewById<ColorfulView>(requireNotNull(lastTab.unreadCounterId))?.setColor(colors.tabUnread)
    }

    override fun setColorPickerAction(openColorPicker: View.(AtthemePreviewKeys) -> Unit) {
        findViewById<ColorfulView>(firstTab.id)?.openColorPicker(actionBarTabActiveText)
        findViewById<ColorfulView>(requireNotNull(firstTab.selectorId))?.openColorPicker(actionBarTabLine)
        findViewById<ColorfulView>(requireNotNull(firstTab.unreadCounterId))?.openColorPicker(chats_tabUnreadUnactiveBackground)

        findViewById<ColorfulView>(middleTab.id)?.openColorPicker(actionBarTabUnactiveText)

        findViewById<ColorfulView>(lastTab.id)?.openColorPicker(actionBarTabUnactiveText)
        findViewById<ColorfulView>(requireNotNull(lastTab.unreadCounterId))?.openColorPicker(chats_tabUnreadUnactiveBackground)
    }

    private data class TabModel(
        val id: Int,
        val unreadCounterId: Int?,
        val selectorId: Int?,
    )
}