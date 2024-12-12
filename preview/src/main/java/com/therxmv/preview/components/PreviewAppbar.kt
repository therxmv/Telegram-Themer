package com.therxmv.preview.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.therxmv.preview.DpValues
import com.therxmv.preview.common.CircleView
import com.therxmv.preview.common.ColorfulView
import com.therxmv.preview.common.RoundedRectangleView
import com.therxmv.preview.model.AppbarColors

class PreviewAppbar(
    private val dpValues: DpValues,
    isInChat: Boolean = false,
    context: Context,
    attr: AttributeSet? = null,
) : RelativeLayout(context, attr) {

    constructor(context: Context, attr: AttributeSet? = null) : this(
        dpValues = DpValues(context),
        isInChat = false,
        context = context,
        attr = attr,
    )

    private val leftIconId = View.generateViewId()
    private val rightIconId = View.generateViewId()
    private val titleId = View.generateViewId()
    private val subtitleId = View.generateViewId()
    private val avatarId = View.generateViewId()

    init {
        drawLeftIcon()

        if (isInChat) {
            drawChatInfo()
        } else {
            drawTitle()
        }

        drawRightIcon()
    }

    private fun drawLeftIcon() {
        RoundedRectangleView.create(
            context = context,
            id = leftIconId,
            width = dpValues.dp30,
            height = dpValues.dp30,
            setUpLayoutParams = {
                addRule(ALIGN_PARENT_TOP)
                addRule(ALIGN_PARENT_START)
            }
        ).also { addView(it) }
    }

    private fun drawRightIcon() {
        RoundedRectangleView.create(
            context = context,
            id = rightIconId,
            width = dpValues.dp14,
            height = dpValues.dp30,
            setUpLayoutParams = {
                addRule(ALIGN_PARENT_TOP)
                addRule(ALIGN_PARENT_END)
            }
        ).also { addView(it) }
    }

    private fun drawTitle() {
        RoundedRectangleView.create(
            context = context,
            id = titleId,
            width = dpValues.dp80,
            height = dpValues.dp20,
            setUpLayoutParams = {
                marginStart = dpValues.dp20
                addRule(END_OF, leftIconId)
                addRule(CENTER_VERTICAL)
            }
        ).also { addView(it) }
    }

    private fun drawChatInfo() {
        drawAvatar()
        drawChatTitle()
        drawChatSubtitle()
    }

    private fun drawAvatar() {
        CircleView.create(
            context = context,
            id = avatarId,
            width = dpValues.dp30,
            height = dpValues.dp30,
            setUpLayoutParams = {
                marginStart = dpValues.dp20
                addRule(END_OF, leftIconId)
            }
        ).also { addView(it) }
    }

    private fun drawChatTitle() {
        RoundedRectangleView.create(
            context = context,
            id = titleId,
            width = dpValues.dp80,
            height = dpValues.dp10,
            setUpLayoutParams = {
                marginStart = dpValues.dp10
                addRule(END_OF, avatarId)
                addRule(ALIGN_TOP, avatarId)
            }
        ).also { addView(it) }
    }

    private fun drawChatSubtitle() {
        RoundedRectangleView.create(
            context = context,
            id = subtitleId,
            width = dpValues.dp50,
            height = dpValues.dp10,
            setUpLayoutParams = {
                marginStart = dpValues.dp10
                addRule(END_OF, avatarId)
                addRule(ALIGN_BOTTOM, avatarId)
            }
        ).also { addView(it) }
    }

    fun setColors(colors: AppbarColors) {
        findViewById<ColorfulView>(leftIconId)?.setColor(colors.appbarIcon)
        findViewById<ColorfulView>(rightIconId)?.setColor(colors.appbarIcon)
        findViewById<ColorfulView>(titleId)?.setColor(colors.appbarTitle)
        findViewById<ColorfulView>(subtitleId)?.setColor(colors.appbarSubtitle)
        findViewById<ColorfulView>(avatarId)?.setColor(colors.appbarAvatar)
    }
}