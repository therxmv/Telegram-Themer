package com.therxmv.telegramthemer.ui.v2.preview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.allViews
import androidx.core.view.setPadding
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.databinding.ChatListPreviewBinding
import com.therxmv.telegramthemer.utils.dpToPx
import com.therxmv.telegramthemer.utils.scaleMargins

class ChatListPreview(
    context: Context,
    attrs: AttributeSet,
) : ConstraintLayout(context, attrs) {
// TODO make it fullscreen
    private var binding: ChatListPreviewBinding = ChatListPreviewBinding.inflate(
        /* inflater = */ LayoutInflater.from(context),
        /* parent = */ this,
        /* attachToParent = */ false,
    )

    init {
        addView(binding.root)

        context.obtainStyledAttributes(attrs, R.styleable.ChatListPreview).apply {
            val factor = getFloat(R.styleable.ChatListPreview_scale_factor, 1f)

            binding.root.allViews.forEach {
                if (it is ScalableView) {
                    it.scaleView(factor)
                }
            }

            binding.contentLayout.setPadding((20.dpToPx(context) * factor).toInt())
            binding.tabs.layout.layoutParams.scaleMargins(factor)

            recycle()
        }
    }

    fun setThemeColors() { // TODO data class? with colors

    }
}