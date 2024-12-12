package com.therxmv.preview.components.chat.message

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import com.therxmv.preview.DpValues
import com.therxmv.preview.common.CircleView
import com.therxmv.preview.common.ColorfulView
import com.therxmv.preview.common.RoundedRectangleView
import com.therxmv.preview.components.chat.message.MessageModel.Date
import com.therxmv.preview.components.chat.message.MessageModel.Message
import com.therxmv.preview.model.MessageColors

class MessageItem(
    private val data: MessageModel,
    private val dpValues: DpValues,
    context: Context,
    attr: AttributeSet? = null,
) : RelativeLayout(context, attr) {

    constructor(
        context: Context,
        attr: AttributeSet?,
    ) : this(
        data = Date,
        dpValues = DpValues(context),
        context = context,
        attr = attr,
    )

    private val dateId = View.generateViewId()
    private val textId = View.generateViewId()

    private val replySenderId = View.generateViewId()
    private val replyTextId = View.generateViewId()
    private val replyLineId = View.generateViewId()

    private val loaderIconId = View.generateViewId()
    private val loaderId = View.generateViewId()
    private val fileNameId = View.generateViewId()
    private val fileInfoId = View.generateViewId()

    private val voiceInfoId = View.generateViewId()
    private val voiceSeekbarId = View.generateViewId()
    private val voiceSeekbarCircleId = View.generateViewId()
    private val voiceSeekbarFillId = View.generateViewId()

    private var backgroundColor = Color.WHITE // TODO default color
    private val _cornerRadius: Float = dpValues.dp14.toFloat()
    private val backgroundPaint: Paint
        get() = Paint().apply {
            isAntiAlias = true
            color = backgroundColor
            style = Paint.Style.FILL
        }

    init {
        when (data) {
            Date -> drawDate()

            is Message -> {
                drawMessage(data)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            /* left = */ 0F,
            /* top = */ 0F,
            /* right = */ width.toFloat(),
            /* bottom = */ height.toFloat(),
            /* rx = */ _cornerRadius,
            /* ry = */ _cornerRadius,
            /* paint = */ backgroundPaint,
        )
        super.onDraw(canvas)
    }

    private fun drawDate() {
        RoundedRectangleView.create(
            context = context,
            id = dateId,
            width = dpValues.dp60,
            height = dpValues.dp14,
        ).also { addView(it) }
    }

    private fun drawMessage(data: Message) {
        setWillNotDraw(false) // To draw background only for messages
        setPadding(dpValues.dp10)

        when (data) {
            is Message.TextMessage -> {
                drawText()

                if (data.isReply) drawReply()
            }

            is Message.FileMessage -> drawFile()
            is Message.VoiceMessage -> drawVoice()
        }
    }

    private fun drawText() {
        RoundedRectangleView.create(
            context = context,
            id = textId,
            width = dpValues.pxOf(160),
            height = dpValues.dp8,
            setUpLayoutParams = {
                if (data is Message.TextMessage && data.isReply) {
                    topMargin = dpValues.dp14
                    addRule(BELOW, replyTextId)
                }
            }
        ).also { addView(it) }
    }

    private fun drawReply() {
        RoundedRectangleView.create(
            context = context,
            id = replyLineId,
            width = dpValues.dp8,
            height = dpValues.dp30,
        ).also { addView(it) }

        RoundedRectangleView.create(
            context = context,
            id = replySenderId,
            width = dpValues.dp50,
            height = dpValues.dp8,
            setUpLayoutParams = {
                marginStart = dpValues.dp8
                addRule(RIGHT_OF, replyLineId)
            }
        ).also { addView(it) }

        RoundedRectangleView.create(
            context = context,
            id = replyTextId,
            width = dpValues.dp80,
            height = dpValues.dp8,
            setUpLayoutParams = {
                topMargin = dpValues.dp10
                addRule(BELOW, replySenderId)

                marginStart = dpValues.dp8
                addRule(RIGHT_OF, replyLineId)
            }
        ).also { addView(it) }
    }

    private fun drawLoader() {
        CircleView.create(
            context = context,
            id = loaderId,
            width = dpValues.dp40,
            height = dpValues.dp40,
        ).also { addView(it) }

        RoundedRectangleView.create(
            context = context,
            id = loaderIconId,
            width = dpValues.dp20,
            height = dpValues.dp20,
            setUpLayoutParams = {
                addRule(ALIGN_START, loaderId)
                addRule(ALIGN_END, loaderId)
                addRule(ALIGN_BOTTOM, loaderId)
                addRule(ALIGN_TOP, loaderId)
                setMargins(dpValues.pxOf(12))
            }
        ).also { addView(it) }
    }

    private fun drawFile() {
        drawLoader()

        RoundedRectangleView.create(
            context = context,
            id = fileNameId,
            width = dpValues.dp60,
            height = dpValues.dp10,
            setUpLayoutParams = {
                marginStart = dpValues.dp10
                addRule(RIGHT_OF, loaderId)
            }
        ).also { addView(it) }

        RoundedRectangleView.create(
            context = context,
            id = fileInfoId,
            width = dpValues.dp100,
            height = dpValues.dp10,
            setUpLayoutParams = {
                marginStart = dpValues.dp10
                addRule(RIGHT_OF, loaderIconId)

                topMargin = dpValues.dp10
                addRule(BELOW, fileNameId)
            }
        ).also { addView(it) }
    }

    private fun drawVoice() {
        drawLoader()

        RoundedRectangleView.create(
            context = context,
            id = voiceInfoId,
            width = dpValues.dp40,
            height = dpValues.dp10,
            setUpLayoutParams = {
                marginStart = dpValues.dp10
                addRule(RIGHT_OF, loaderId)

                topMargin = dpValues.dp8
                addRule(BELOW, voiceSeekbarFillId)
            }
        ).also { addView(it) }

        RoundedRectangleView.create(
            context = context,
            id = voiceSeekbarFillId,
            width = dpValues.dp80,
            height = dpValues.dp10,
            setUpLayoutParams = {
                topMargin = dpValues.dp10
                marginStart = dpValues.dp10
                addRule(RIGHT_OF, loaderId)
            }
        ).also { addView(it) }

        CircleView.create(
            context = context,
            id = voiceSeekbarCircleId,
            width = dpValues.dp14,
            height = dpValues.dp14,
            setUpLayoutParams = {
                topMargin = dpValues.dp7
                addRule(ALIGN_END, voiceSeekbarFillId)
            }
        ).also { addView(it) }

        RoundedRectangleView.create(
            context = context,
            id = voiceSeekbarId,
            width = dpValues.dp40,
            height = dpValues.dp10,
            setUpLayoutParams = {
                topMargin = dpValues.dp10
                marginStart = dpValues.dp4
                addRule(RIGHT_OF, voiceSeekbarCircleId)
            }
        ).also { addView(it) }
    }

    fun setColors(colors: MessageColors) {
        when (data) {
            Date -> {
                findViewById<ColorfulView>(dateId)?.setColor(colors.date)
            }

            is Message.TextMessage -> {
                findViewById<ColorfulView>(textId)?.setColor(colors.text)

                if (data.isReply) {
                    findViewById<ColorfulView>(replySenderId)?.setColor(colors.replyColors.sender)
                    findViewById<ColorfulView>(replyTextId)?.setColor(colors.replyColors.text)
                    findViewById<ColorfulView>(replyLineId)?.setColor(colors.replyColors.line)
                }
            }

            is Message.FileMessage -> {
                findViewById<ColorfulView>(loaderId)?.setColor(colors.fileColors.loader)
                findViewById<ColorfulView>(loaderIconId)?.setColor(colors.background)
                findViewById<ColorfulView>(fileNameId)?.setColor(colors.fileColors.name)
                findViewById<ColorfulView>(fileInfoId)?.setColor(colors.fileColors.info)
            }

            is Message.VoiceMessage -> {
                findViewById<ColorfulView>(loaderId)?.setColor(colors.fileColors.loader)
                findViewById<ColorfulView>(loaderIconId)?.setColor(colors.background)
                findViewById<ColorfulView>(voiceInfoId)?.setColor(colors.voiceColors.info)
                findViewById<ColorfulView>(voiceSeekbarId)?.setColor(colors.voiceColors.seekbar)
                findViewById<ColorfulView>(voiceSeekbarCircleId)?.setColor(colors.voiceColors.seekbarFill)
                findViewById<ColorfulView>(voiceSeekbarFillId)?.setColor(colors.voiceColors.seekbarFill)
            }
        }

        backgroundColor = colors.background
        invalidate()
    }
}