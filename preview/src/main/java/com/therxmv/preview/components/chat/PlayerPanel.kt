package com.therxmv.preview.components.chat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.therxmv.preview.DpValues
import com.therxmv.preview.common.preview.ClickablePreview
import com.therxmv.preview.common.preview.ColorfulPreview
import com.therxmv.preview.common.view.ColorfulView
import com.therxmv.preview.common.view.RoundedRectangleView
import com.therxmv.preview.model.PlayerPanelColors
import com.therxmv.preview.utils.AtthemePreviewKeys
import com.therxmv.preview.utils.AtthemePreviewKeys.inappPlayerClose
import com.therxmv.preview.utils.AtthemePreviewKeys.inappPlayerPerformer
import com.therxmv.preview.utils.AtthemePreviewKeys.inappPlayerPlayPause

class PlayerPanel(
    private val dpValues: DpValues,
    context: Context,
    attr: AttributeSet? = null,
) : RelativeLayout(context, attr),
    ColorfulPreview<PlayerPanelColors>,
    ClickablePreview {

    constructor(context: Context, attr: AttributeSet? = null) : this(
        dpValues = DpValues(context),
        context = context,
        attr = attr,
    )

    private val playButtonId = View.generateViewId()
    private val trackNameId = View.generateViewId()
    private val speedButtonId = View.generateViewId()
    private val closeButtonId = View.generateViewId()

    init {
        drawPlayButton()
        drawTrackName()
        drawSpeedButton()
        drawCloseButton()
    }

    private fun drawPlayButton() {
        RoundedRectangleView.create(
            context = context,
            id = playButtonId,
            width = dpValues.dp10,
            height = dpValues.dp10,
            setUpLayoutParams = {
                addRule(ALIGN_PARENT_START)
            }
        ).also { addView(it) }
    }

    private fun drawTrackName() {
        RoundedRectangleView.create(
            context = context,
            id = trackNameId,
            width = dpValues.dp80,
            height = dpValues.dp10,
            setUpLayoutParams = {
                marginStart = dpValues.dp10
                addRule(END_OF, playButtonId)
            }
        ).also { addView(it) }
    }

    private fun drawSpeedButton() {
        RoundedRectangleView.create(
            context = context,
            id = speedButtonId,
            width = dpValues.dp20,
            height = dpValues.dp10,
            setUpLayoutParams = {
                marginEnd = dpValues.dp10
                addRule(START_OF, closeButtonId)
            }
        ).also { addView(it) }
    }

    private fun drawCloseButton() {
        RoundedRectangleView.create(
            context = context,
            id = closeButtonId,
            width = dpValues.dp10,
            height = dpValues.dp10,
            setUpLayoutParams = {
                addRule(ALIGN_PARENT_END)
            }
        ).also { addView(it) }
    }

    override fun setColors(colors: PlayerPanelColors) {
        findViewById<ColorfulView>(playButtonId)?.setColor(colors.play)
        findViewById<ColorfulView>(trackNameId)?.setColor(colors.name)
        findViewById<ColorfulView>(speedButtonId)?.setColor(colors.icons)
        findViewById<ColorfulView>(closeButtonId)?.setColor(colors.icons)
    }

    override fun setColorPickerAction(openColorPicker: View.(AtthemePreviewKeys) -> Unit) {
        findViewById<ColorfulView>(playButtonId)?.openColorPicker(inappPlayerPlayPause)
        findViewById<ColorfulView>(trackNameId)?.openColorPicker(inappPlayerPerformer)
        findViewById<ColorfulView>(speedButtonId)?.openColorPicker(inappPlayerClose)
        findViewById<ColorfulView>(closeButtonId)?.openColorPicker(inappPlayerClose)
    }
}