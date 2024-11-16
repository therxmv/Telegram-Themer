package com.therxmv.preview.components.chatslist

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.therxmv.preview.DpValues
import com.therxmv.preview.common.CircleView
import com.therxmv.preview.common.ColorfulView
import com.therxmv.preview.common.HorizontalLineView
import com.therxmv.preview.common.RoundedRectangleView
import com.therxmv.preview.model.TabsColors

class Tabs(
    dpValues: DpValues,
    context: Context,
    attr: AttributeSet? = null,
) : RelativeLayout(context, attr) {

    constructor(context: Context, attr: AttributeSet? = null) : this(DpValues(context), context, attr)

    private val tabWidth = dpValues.dp50
    private val tabHeight = dpValues.dp14
    private val margin = dpValues.dp4

    companion object {
        private val tabsIds = listOf(
            TabData(
                id = generateViewId(),
                unreadCounterId = generateViewId(),
                selectorId = generateViewId(),
            ),
            TabData(
                id = generateViewId(),
                unreadCounterId = null,
                selectorId = null,
            ),
            TabData(
                id = generateViewId(),
                unreadCounterId = generateViewId(),
                selectorId = null,
            ),
        )
    }

    init {
        createTabs()
    }

    private fun createTabs() { // TODO can be refactored
        val first = tabsIds[0]
        val middle = tabsIds[1]
        val last = tabsIds[2]

        HorizontalLineView(context).apply {
            id = first.id
            layoutParams = LayoutParams(
                /* w = */ tabWidth,
                /* h = */ tabHeight,
            ).apply {
                addRule(ALIGN_PARENT_TOP)
                addRule(ALIGN_PARENT_START)
            }
            addView(this@apply)
        }

        RoundedRectangleView(context).apply {
            id = first.selectorId!!
            layoutParams = LayoutParams(
                /* w = */ tabHeight + tabWidth + margin,
                /* h = */ margin,
            ).apply {
                topMargin = margin
                addRule(BELOW, first.id)
            }
            addView(this@apply)
        }

        CircleView(context).apply {
            id = first.unreadCounterId!!
            layoutParams = LayoutParams(
                /* w = */ tabHeight,
                /* h = */ tabHeight,
            ).apply {
                marginStart = margin
                addRule(END_OF, first.id)
            }
            addView(this@apply)
        }

        HorizontalLineView(context).apply {
            id = middle.id
            layoutParams = LayoutParams(
                /* w = */ tabWidth,
                /* h = */ tabHeight,
            ).apply {
                addRule(CENTER_HORIZONTAL)
            }
            addView(this@apply)
        }

        HorizontalLineView(context).apply {
            id = last.id
            layoutParams = LayoutParams(
                /* w = */ tabWidth,
                /* h = */ tabHeight,
            ).apply {
                marginEnd = margin
                addRule(START_OF, last.unreadCounterId!!)
            }
            addView(this@apply)
        }

        CircleView(context).apply {
            id = last.unreadCounterId!!
            layoutParams = LayoutParams(
                /* w = */ tabHeight,
                /* h = */ tabHeight,
            ).apply {
                addRule(ALIGN_PARENT_END)
            }
            addView(this@apply)
        }
    }

    fun setColors(colors: TabsColors) {
        val first = tabsIds[0]
        val middle = tabsIds[1]
        val last = tabsIds[2]

        findViewById<ColorfulView>(first.id)?.setColor(colors.selectedTab)
        findViewById<ColorfulView>(first.selectorId!!)?.setColor(colors.tabSelector)
        findViewById<ColorfulView>(first.unreadCounterId!!)?.setColor(colors.tabUnread)

        findViewById<ColorfulView>(middle.id)?.setColor(colors.tab)

        findViewById<ColorfulView>(last.id)?.setColor(colors.tab)
        findViewById<ColorfulView>(last.unreadCounterId!!)?.setColor(colors.tabUnread)
    }

    private data class TabData(
        val id: Int,
        val unreadCounterId: Int?,
        val selectorId: Int?,
    )
}