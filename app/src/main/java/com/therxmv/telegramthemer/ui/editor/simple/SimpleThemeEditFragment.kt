package com.therxmv.telegramthemer.ui.editor.simple

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.findNavController
import com.google.android.material.color.MaterialColors
import com.therxmv.preview.model.PreviewColorsModel
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.databinding.FragmentSimpleThemeEditBinding
import com.therxmv.telegramthemer.domain.model.Platform
import com.therxmv.telegramthemer.domain.model.TemplateCapabilities
import com.therxmv.telegramthemer.domain.model.TemplateStyle
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.ui.animator.FadeAnimator
import com.therxmv.telegramthemer.ui.animator.PlatformSelectorAnimator.animateSlideTo
import com.therxmv.telegramthemer.ui.animator.PlatformSelectorAnimator.animateTextColorTo
import com.therxmv.telegramthemer.ui.animator.RadiusAnimator.animateToCircle
import com.therxmv.telegramthemer.ui.base.BaseBindingFragment
import com.therxmv.telegramthemer.ui.extensions.isMonetAvailable
import com.therxmv.telegramthemer.ui.extensions.toVisibility
import javax.inject.Inject

class SimpleThemeEditFragment : BaseBindingFragment<FragmentSimpleThemeEditBinding>(),
    SimpleThemeEditContract.View {

    companion object {
        private const val GRADIENT_DURATION = 700L
    }

    @Inject
    lateinit var presenter: SimpleThemeEditContract.Presenter

    private var previewAnimation: ValueAnimator? = null
    private var platformSelectionInitialized = false

    // Each accent swatch slot, as (the ring container that gets clicked/
    // selected, its inner dot) - rebuilt on every view creation (see
    // onViewCreated) since Navigation destroys and recreates this view when
    // returning from the advanced screen while keeping the same fragment
    // instance.
    private var accentSwatches: List<Pair<View, View>> = emptyList()

    // Rendering-only cache so the style dropdown's adapter isn't rebuilt on
    // every render pass (this screen re-renders on every ThemeState change,
    // including continuous ones like dragging the custom color picker).
    private var lastStyleIds: List<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = onCreateView(inflater, container, FragmentSimpleThemeEditBinding::inflate)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accentSwatches = listOf(
            binding.swatch1 to binding.swatch1Dot,
            binding.swatch2 to binding.swatch2Dot,
            binding.swatch3 to binding.swatch3Dot,
            binding.swatch4 to binding.swatch4Dot,
            binding.swatch5 to binding.swatch5Dot,
            binding.swatch6 to binding.swatch6Dot,
        )

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
        lastStyleIds = null
        presenter.detachView()
        super.onDestroyView()
    }

    override fun setUpColorPicker(onOpenColorPicker: () -> Unit) {
        binding.pickerButton.setOnClickListener { onOpenColorPicker() }
    }

    override fun setUpAccentSwatches(onSwatchClicked: (index: Int) -> Unit) {
        accentSwatches.forEachIndexed { index, (swatch, _) ->
            swatch.setOnClickListener { onSwatchClicked(index) }
        }
    }

    override fun setUpStyleSelector(onStyleSelected: (index: Int) -> Unit) {
        binding.baseSelectorItems.setOnItemClickListener { _, _, position, _ ->
            onStyleSelected(position)
        }
    }

    override fun setUpToggles(
        onDarkToggled: (Boolean) -> Unit,
        onAmoledToggled: (Boolean) -> Unit,
        onMonetToggled: (Boolean) -> Unit,
        onGradientToggled: (Boolean) -> Unit,
    ) {
        binding.chipDark.setOnClickListener { onDarkToggled(binding.chipDark.isChecked) }
        binding.chipAmoled.setOnClickListener { onAmoledToggled(binding.chipAmoled.isChecked) }
        binding.chipMonet.setOnClickListener { onMonetToggled(binding.chipMonet.isChecked) }
        binding.chipGradient.setOnClickListener { onGradientToggled(binding.chipGradient.isChecked) }
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

    override fun renderOptionsCard(
        themeState: ThemeState,
        styles: List<TemplateStyle>,
        capabilities: TemplateCapabilities,
        recentAccentColors: List<Int>,
    ) {
        requireActivity().runOnUiThread {
            applyAccentSwatches(recentAccentColors, themeState.accent)

            val styleIds = styles.map { it.id }
            if (styleIds != lastStyleIds) {
                binding.baseSelectorItems.setAdapter(
                    ArrayAdapter(requireContext(), R.layout.dropdown_item, styles.map { it.label })
                )
                lastStyleIds = styleIds
            }
            val styleLabel = styles.firstOrNull { it.id == themeState.style }?.label ?: themeState.style
            if (binding.baseSelectorItems.text?.toString() != styleLabel) {
                binding.baseSelectorItems.setText(styleLabel, false)
            }

            binding.chipDark.isChecked = themeState.isDark
            // Only shown when it's an actual choice - a style with just one
            // variant has nothing to toggle, isDark is simply fixed to it.
            binding.chipDark.visibility = (capabilities.hasLight && capabilities.hasDark).toVisibility()

            // Amoled is only a variant of dark mode (no separate template file),
            // so it's selectable whenever dark is reachable at all, toggle or not.
            binding.chipAmoled.isChecked = themeState.isAmoled
            binding.chipAmoled.visibility = capabilities.hasDark.toVisibility()

            binding.chipGradient.isChecked = themeState.isGradient
            binding.chipGradient.visibility = capabilities.hasGradient.toVisibility()

            binding.chipMonet.isChecked = themeState.isMonet
            binding.chipMonet.visibility = isMonetAvailable().toVisibility()
        }
    }

    private fun applyAccentSwatches(recentAccentColors: List<Int>, accent: Int) {
        val strokeWidth = resources.getDimension(R.dimen.accent_swatch_ring_stroke).toInt()
        accentSwatches.forEachIndexed { index, (swatch, dot) ->
            val color = recentAccentColors.getOrNull(index) ?: return@forEachIndexed
            val isSelected = color == accent
            // Drawn directly rather than via a tinted selector drawable shared
            // across the 5 swatches - a stroke-only oval inside a
            // StateListDrawable doesn't reliably re-tint per instance here.
            swatch.background = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setStroke(strokeWidth, if (isSelected) color else Color.TRANSPARENT)
            }
            dot.backgroundTintList = ColorStateList.valueOf(color)
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

    // Swings the gradient's poles to their opposite ends and back
    override fun startPreviewAnimation(newGradient: IntArray, oldGradient: IntArray) {
        val evaluator = ArgbEvaluator()

        previewAnimation = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = GRADIENT_DURATION
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener { animation ->
                val fraction = animation.animatedFraction

                val currentColors = newGradient
                    .zip(oldGradient) { start, end -> evaluator.evaluate(fraction, start, end) as Int }
                    .toIntArray()

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