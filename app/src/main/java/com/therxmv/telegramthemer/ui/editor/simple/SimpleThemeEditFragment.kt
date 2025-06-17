package com.therxmv.telegramthemer.ui.editor.simple

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
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

    companion object {
        private const val GRADIENT_DURATION = 1000L
    }

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
        setPreviewGradient(colors.previewGradient.toIntArray())
        binding.chatListPreview.setColors(colors)
        binding.chatPreview.setColors(colors)
    }

    override fun startPreviewAnimation(newGradient: IntArray, oldGradient: IntArray) {
        val evaluator = ArgbEvaluator()

        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = GRADIENT_DURATION
            addUpdateListener { animation ->
                val fraction = animation.animatedFraction

                val currentColors = newGradient
                    .zip(oldGradient).map { (start, end) ->
                        evaluator.evaluate(fraction, start, end) as Int
                    }.toIntArray()
                    .zip(newGradient).map { (start, end) ->
                        evaluator.evaluate(fraction, start, end) as Int
                    }.toIntArray()

                setPreviewGradient(currentColors)
            }
            start()
        }
    }

    private fun setPreviewGradient(gradient: IntArray) {
        val drawable = GradientDrawable().apply {
            colors = gradient
            orientation = GradientDrawable.Orientation.TL_BR
            gradientType = GradientDrawable.LINEAR_GRADIENT
            shape = GradientDrawable.OVAL

        }
        binding.previewBackground.background = drawable
    }
}