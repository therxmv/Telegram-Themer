package com.therxmv.telegramthemer.ui.editor.simple

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.findNavController
import com.google.android.material.color.MaterialColors
import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.databinding.FragmentSimpleThemeEditBinding
import com.therxmv.telegramthemer.domain.model.Platform
import com.therxmv.telegramthemer.ui.animator.FadeAnimator
import com.therxmv.telegramthemer.ui.animator.PlatformSelectorAnimator.animateSlideTo
import com.therxmv.telegramthemer.ui.animator.PlatformSelectorAnimator.animateTextColorTo
import com.therxmv.telegramthemer.ui.animator.RadiusAnimator.animateToCircle
import com.therxmv.telegramthemer.ui.base.BaseBindingFragment
import javax.inject.Inject

class SimpleThemeEditFragment : BaseBindingFragment<FragmentSimpleThemeEditBinding>(),
    SimpleThemeEditContract.View {

    companion object {
        private const val GRADIENT_DURATION = 1000L
    }

    @Inject
    lateinit var presenter: SimpleThemeEditContract.Presenter

    private var previewAnimation: ValueAnimator? = null
    private var platformSelectionInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = onCreateView(inflater, container, FragmentSimpleThemeEditBinding::inflate)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chatPreview.doOnPreDraw { // Fragment should wait until preview is drawn
            presenter.attachView(this@SimpleThemeEditFragment)
        }
        setUpAdvancedButton()
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator {
        return FadeAnimator.inside().takeIf { enter } ?: FadeAnimator.outside()
    }

    override fun onDestroyView() {
        previewAnimation?.cancel()
        previewAnimation = null
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

    override fun setUpPlatformButtons(onAndroidClick: () -> Unit, onIosClick: () -> Unit) {
        binding.androidPlatformButton.setOnClickListener { onAndroidClick() }
        binding.iosPlatformButton.setOnClickListener { onIosClick() }
    }

    override fun setUpExportButton(onClick: () -> Unit) {
        binding.exportContainer.setOnClickListener {
            it.background.animateToCircle(requireContext())

            onClick()
        }
    }

    private fun setUpAdvancedButton() {
        binding.advancedEditButton.setOnClickListener {
            val layers = it.background as LayerDrawable
            layers.findDrawableByLayerId(R.id.edit_background).animateToCircle(requireContext())

            findNavController().navigate(R.id.action_simpleThemeEditFragment_to_advancedThemeEditFragment)
        }
    }

    override fun setColorPickerColors(accent: Int, background: Int) {
        requireActivity().runOnUiThread {
            binding.pickerButton.backgroundTintList = ColorStateList.valueOf(accent)
            binding.pickerButton.foregroundTintList = ColorStateList.valueOf(background)
        }
    }

    override fun setPreviewColors(colors: PreviewColorsModel) {
        requireActivity().runOnUiThread {
            setPreviewGradient(colors.previewGradient.toIntArray())
            binding.chatListPreview.setColors(colors)
            binding.chatPreview.setColors(colors)
        }
    }

    override fun setPlatformSelection(platform: Platform) {
        requireActivity().runOnUiThread {
            // Defer until the row has a final, stable measured width - reading widths any
            // earlier (e.g. straight off doOnPreDraw) can catch an intermediate ConstraintLayout
            // percent-width pass and produce a wrong (too small) offset.
            binding.platformSelectorContainer.doOnLayout {
                applyPlatformSelection(platform)
            }
        }
    }

    private fun applyPlatformSelection(platform: Platform) {
        val indicator = binding.platformSelectorIndicator
        val androidButton = binding.androidPlatformButton
        val iosButton = binding.iosPlatformButton
        val isAndroidSelected = platform == Platform.ANDROID
        val targetX = if (isAndroidSelected) 0f else (iosButton.left - androidButton.left).toFloat()

        val selectedTextColor = MaterialColors.getColor(indicator, com.google.android.material.R.attr.colorOnPrimary)
        val unselectedTextColor = MaterialColors.getColor(indicator, com.google.android.material.R.attr.colorPrimary)
        val androidTextColor = selectedTextColor.takeIf { isAndroidSelected } ?: unselectedTextColor
        val iosTextColor = selectedTextColor.takeUnless { isAndroidSelected } ?: unselectedTextColor

        // First render has nothing meaningful to slide/fade from - snap instantly.
        if (platformSelectionInitialized) {
            indicator.animateSlideTo(targetX)
            androidButton.animateTextColorTo(androidTextColor)
            iosButton.animateTextColorTo(iosTextColor)
        } else {
            indicator.translationX = targetX
            androidButton.setTextColor(androidTextColor)
            iosButton.setTextColor(iosTextColor)
            platformSelectionInitialized = true
        }
    }

    override fun startPreviewAnimation(newGradient: IntArray, oldGradient: IntArray) {
        val evaluator = ArgbEvaluator()

        previewAnimation = ValueAnimator.ofFloat(0f, 1f).apply {
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