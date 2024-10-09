package com.therxmv.telegramthemer.ui.v2.preview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

class PreviewTabs(
    scaleFactor: Float,
    context: Context,
    attr: AttributeSet? = null,
) : RelativeLayout(context, attr) {

    constructor(context: Context, attr: AttributeSet? = null) : this(1f, context, attr)

    private val dpValues = DpValues(context, scaleFactor)
    private val tabWidth = dpValues.dp50
    private val tabHeight = dpValues.dp14
    private val margin = dpValues.dp4

    companion object {
        private val tabsIds = listOf(
            TabData(
                id = View.generateViewId(),
                unreadCounterId = View.generateViewId(),
                selectorId = View.generateViewId(),
            ),
            TabData(
                id = View.generateViewId(),
                unreadCounterId = null,
                selectorId = null,
            ),
            TabData(
                id = View.generateViewId(),
                unreadCounterId = View.generateViewId(),
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

    private data class TabData(
        val id: Int,
        val unreadCounterId: Int?,
        val selectorId: Int?,
    )
}