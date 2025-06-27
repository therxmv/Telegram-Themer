package com.therxmv.telegramthemer.ui.editor.advanced

import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.lifecycleScope
import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.preview.utils.AtthemePreviewKeys
import com.therxmv.preview.utils.getColor
import com.therxmv.preview.utils.setOnSwipeListener
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.databinding.FragmentAdvancedThemeEditBinding
import com.therxmv.telegramthemer.ui.base.BaseBindingFragment
import com.therxmv.telegramthemer.utils.startRadiusAnimation
import javax.inject.Inject

class AdvancedThemeEditFragment : BaseBindingFragment<FragmentAdvancedThemeEditBinding>(),
    AdvancedThemeEditContract.View {

    @Inject
    lateinit var presenter: AdvancedThemeEditContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = onCreateView(inflater, container, FragmentAdvancedThemeEditBinding::inflate)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chatPreview.doOnPreDraw { // Fragment should wait until preview is drawn
            presenter.attachView(this@AdvancedThemeEditFragment, lifecycleScope)
        }
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun setPreviewColors(colors: PreviewColorsModel) {
        binding.chatListPreview.setColors(colors)
        binding.chatPreview.setColors(colors)
    }

    override fun setUpOnPreviewClick(action: (AtthemePreviewKeys, Int) -> Unit) {
        binding.chatListPreview.setColorPickerAction { key ->
            setOnSwipeListener(
                onTap = { action(key, it.getColor()) },
                onSwipeLeft = {
                    binding.motionLayout.transitionToEnd()
                },
            )
        }
        binding.chatPreview.setColorPickerAction { key ->
            setOnSwipeListener(
                onTap = { action(key, it.getColor()) },
                onSwipeRight = {
                    binding.motionLayout.transitionToStart()
                },
            )
        }
    }

    override fun setUpResetButton(onClick: () -> Unit) {
        binding.resetButton.setOnClickListener {
            val layers = it.background as LayerDrawable
            layers.findDrawableByLayerId(R.id.reset_background).startRadiusAnimation(requireContext())

            onClick()
        }
    }
}