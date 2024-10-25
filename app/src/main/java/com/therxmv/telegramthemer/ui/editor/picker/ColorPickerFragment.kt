package com.therxmv.telegramthemer.ui.editor.picker

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.allViews
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.therxmv.telegramthemer.databinding.FragmentColorPickerBinding
import com.therxmv.telegramthemer.ui.base.BaseBindingBottomSheetFragment
import com.therxmv.telegramthemer.ui.base.BaseBindingDialogFragment.Companion.DEFAULT_DIM
import top.defaults.colorpicker.AlphaSliderView
import top.defaults.colorpicker.BrightnessSliderView
import top.defaults.colorpicker.ColorWheelView

class ColorPickerFragment : BaseBindingBottomSheetFragment<FragmentColorPickerBinding>() {

    companion object {
        private const val TRANSPARENCY_VALUE = 0.3f
        
        private const val INITIAL_COLOR_KEY = "InitialColorKey"

        fun createInstance(initialColor: Int) = ColorPickerFragment().apply {
            arguments = Bundle().apply {
                putInt(INITIAL_COLOR_KEY, initialColor)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = onCreateView(inflater, container, FragmentColorPickerBinding::inflate)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpInitialColor()
        setUpTransparentListeners()
        setUpInputListeners()
        subscribeOnColorChanges()
    }

    private fun subscribeOnColorChanges() {
        binding.colorPicker.subscribe { color, _, _ ->
            (requireActivity() as? ColorPickerSubscriber)?.onColorChanged(color)
        }
    }

    private fun setUpInitialColor() {
        val initialColor = arguments?.getInt(INITIAL_COLOR_KEY) ?: binding.colorPicker.color
        updateInputValues(initialColor)
        binding.colorPicker.setInitialColor(initialColor)
    }

    private fun setUpInputListeners() {
        val inputs = listOf(binding.redInput, binding.greenInput, binding.blueInput)

        inputs.forEach {
            it.apply {
                onDonePressed()
                onTextChangeListener()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateInputValues(color: Int = binding.colorPicker.color) {
        val red = (color shr 16) and 0xFF
        val green = (color shr 8) and 0xFF
        val blue = color and 0xFF

        binding.redInput.setText(red.toString())
        binding.greenInput.setText(green.toString())
        binding.blueInput.setText(blue.toString())
    }

    private fun TextInputEditText.onTextChangeListener() = addTextChangedListener {
        setColorFromInputs()
    }

    private fun TextInputEditText.onDonePressed() = setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            this.clearFocus()
        }
        false
    }

    private fun setColorFromInputs() {
        val red = binding.redInput.text.getValue()
        val green = binding.greenInput.text.getValue()
        val blue = binding.blueInput.text.getValue()

        binding.colorPicker.setInitialColor(
            Color.rgb(red, green, blue)
        )
    }

    private fun Editable?.getValue(): Int {
        val value = this.toString().toIntOrNull() ?: return 0

        if (value > 255) {
            this?.replace(0, 3, "255")
        }

        return this.toString().toInt()
    }

    private fun setUpTransparentListeners() {
        val colorWheel = binding.colorPicker.allViews.firstOrNull { it is ColorWheelView }
        val brightnessSlider = binding.colorPicker.allViews.firstOrNull { it is BrightnessSliderView }
        val alphaSlider = binding.colorPicker.allViews.firstOrNull { it is AlphaSliderView }

        colorWheel?.makeTransparentOnTouch()
        brightnessSlider?.makeTransparentOnTouch()
        alphaSlider?.makeTransparentOnTouch()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun View.makeTransparentOnTouch() = setOnTouchListener { view, motionEvent ->
        view.onTouchEvent(motionEvent)
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                dialog.window?.attributes?.alpha = TRANSPARENCY_VALUE
                dialog.window?.setDimAmount(0f)
            }

            MotionEvent.ACTION_UP -> {
                updateInputValues()
                dialog.window?.attributes?.alpha = 1f
                dialog.window?.setDimAmount(DEFAULT_DIM)
            }
        }

        true
    }
}