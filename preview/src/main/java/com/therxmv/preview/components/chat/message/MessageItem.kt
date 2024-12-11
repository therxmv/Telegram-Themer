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
import com.therxmv.preview.common.HorizontalLineView
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

    companion object {
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
    }

    private var backgroundColor = Color.WHITE // TODO default color
    private val _cornerRadius: Float = dpValues.dp14.toFloat()
    private val backgroundPaint: () -> Paint = {
        Paint().apply {
            isAntiAlias = true
            color = backgroundColor
            style = Paint.Style.FILL
        }
    }

    init {
        when (data) {
            Date -> addDate()

            is Message -> {
                addMessage(data)
            }
        }
    }

    private fun addDate() {
        HorizontalLineView(context).apply {
            id = dateId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp60,
                /* h = */ dpValues.dp14,
            )
            addView(this)
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            0F,
            0F,
            width.toFloat(),
            height.toFloat(),
            _cornerRadius,
            _cornerRadius,
            backgroundPaint(),
        )
        super.onDraw(canvas)
    }

    private fun addMessage(data: Message) {
        setWillNotDraw(false)
        setPadding(dpValues.dp10)

        when (data) {
            is Message.TextMessage -> {
                addText()

                if (data.isReply) addReply()
            }

            is Message.FileMessage -> addFile()
            is Message.VoiceMessage -> addVoice()
        }
    }

    private fun addText() {
        HorizontalLineView(context).apply {
            id = textId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp160,
                /* h = */ dpValues.dp8,
            ).apply {
                if (data is Message.TextMessage && data.isReply) {
                    topMargin = dpValues.dp14
                    addRule(BELOW, replyTextId)
                }
            }
            addView(this)
        }
    }

    private fun addReply() {
        RoundedRectangleView(context).apply {
            id = replyLineId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp8,
                /* h = */ dpValues.dp30,
            )
            addView(this)
        }

        HorizontalLineView(context).apply {
            id = replySenderId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp50,
                /* h = */ dpValues.dp8,
            ).apply {
                marginStart = dpValues.dp8
                addRule(RIGHT_OF, replyLineId)
            }
            addView(this)
        }

        HorizontalLineView(context).apply {
            id = replyTextId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp80,
                /* h = */ dpValues.dp8,
            ).apply {
                topMargin = dpValues.dp10
                addRule(BELOW, replySenderId)

                marginStart = dpValues.dp8
                addRule(RIGHT_OF, replyLineId)
            }
            addView(this)
        }
    }

    private fun addLoader() {
        CircleView(context).apply {
            id = loaderId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp40,
                /* h = */ dpValues.dp40,
            )
            addView(this)
        }

        RoundedRectangleView(context).apply {
            id = loaderIconId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp20,
                /* h = */ dpValues.dp20,
            ).apply {
                addRule(ALIGN_START, loaderId)
                addRule(ALIGN_END, loaderId)
                addRule(ALIGN_BOTTOM, loaderId)
                addRule(ALIGN_TOP, loaderId)
                setMargins(dpValues.dp10)
            }
            addView(this)
        }
    }

    private fun addFile() {
        addLoader()

        HorizontalLineView(context).apply {
            id = fileNameId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp60,
                /* h = */ dpValues.dp10,
            ).apply {
                marginStart = dpValues.dp10
                addRule(RIGHT_OF, loaderId)
            }
            addView(this)
        }

        HorizontalLineView(context).apply {
            id = fileInfoId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp100,
                /* h = */ dpValues.dp10,
            ).apply {
                marginStart = dpValues.dp10
                addRule(RIGHT_OF, loaderIconId)

                topMargin = dpValues.dp10
                addRule(BELOW, fileNameId)
            }
            addView(this)
        }
    }

    private fun addVoice() {
        addLoader()

        HorizontalLineView(context).apply {
            id = voiceInfoId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp40,
                /* h = */ dpValues.dp10,
            ).apply {
                marginStart = dpValues.dp10
                addRule(RIGHT_OF, loaderId)

                topMargin = dpValues.dp8
                addRule(BELOW, voiceSeekbarFillId)
            }
            addView(this)
        }

        HorizontalLineView(context).apply {
            id = voiceSeekbarFillId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp80,
                /* h = */ dpValues.dp10,
            ).apply {
                topMargin = dpValues.dp10
                marginStart = dpValues.dp10
                addRule(RIGHT_OF, loaderId)
            }
            addView(this)
        }

        CircleView(context).apply {
            id = voiceSeekbarCircleId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp14,
                /* h = */ dpValues.dp14,
            ).apply {
                topMargin = dpValues.dp7
                addRule(ALIGN_END, voiceSeekbarFillId)
            }
            addView(this)
        }

        HorizontalLineView(context).apply {
            id = voiceSeekbarId
            layoutParams = LayoutParams(
                /* w = */ dpValues.dp40,
                /* h = */ dpValues.dp10,
            ).apply {
                topMargin = dpValues.dp10
                marginStart = dpValues.dp8
                addRule(RIGHT_OF, voiceSeekbarCircleId)
            }
            addView(this)
        }
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