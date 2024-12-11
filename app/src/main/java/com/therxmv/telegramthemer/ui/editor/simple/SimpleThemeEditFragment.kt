package com.therxmv.telegramthemer.ui.editor.simple

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.lifecycleScope
import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.telegramthemer.databinding.FragmentSimpleThemeEditBinding
import com.therxmv.telegramthemer.ui.base.BaseBindingFragment
import javax.inject.Inject

class SimpleThemeEditFragment : BaseBindingFragment<FragmentSimpleThemeEditBinding>(),
    SimpleThemeEditContract.View {

    @Inject
    lateinit var presenter: SimpleThemeEditContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = onCreateView(inflater, container, FragmentSimpleThemeEditBinding::inflate)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chatPreview.doOnPreDraw { // Fragment should wait until preview is drawn
            presenter.attachView(this@SimpleThemeEditFragment, lifecycleScope)
        }
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun setUpColorPickerButton(onClick: () -> Unit) {
        binding.pickerContainer.setOnClickListener {
            onClick()
        }
    }

    override fun setUpMoreOptionsButton(onClick: () -> Unit) {
        binding.moreContainer.setOnClickListener {
            onClick()
        }
    }

    override fun setUpExportButton(onClick: () -> Unit) {
        binding.exportContainer.setOnClickListener {
            onClick()
        }
    }

    override fun setColorPickerColors(accent: Int, background: Int) {
        binding.pickerButton.backgroundTintList = ColorStateList.valueOf(accent)
        binding.pickerButton.foregroundTintList = ColorStateList.valueOf(background)
    }

    override fun setPreviewColors(colors: PreviewColorsModel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            binding.previewBackground.outlineSpotShadowColor = colors.accent
        }
        binding.previewBackground.backgroundTintList = ColorStateList.valueOf(colors.previewBackground)
        binding.chatListPreview.setColors(colors)
        binding.chatPreview.setColors(colors)
    }
}