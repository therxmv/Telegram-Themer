package com.therxmv.telegramthemer.ui.editor.simple

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.therxmv.preview.PreviewColorsModel
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
        presenter.attachView(this@SimpleThemeEditFragment, lifecycleScope)
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun setUpColorPickerButton(onClick: () -> Unit) {
        binding.pickerButton.setOnClickListener {
            onClick()
        }
    }

    override fun setColorPickerBackground(color: Int) {
        binding.pickerButton.backgroundTintList = ColorStateList.valueOf(color)
    }

    override fun setThemeColors(colors: PreviewColorsModel) {
        binding.chatListPreview.setColors(colors)
        binding.chatPreview.setColors(colors)
    }
}