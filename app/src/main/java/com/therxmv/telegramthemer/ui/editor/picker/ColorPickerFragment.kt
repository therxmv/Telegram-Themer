package com.therxmv.telegramthemer.ui.editor.picker

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.allViews
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.therxmv.telegramthemer.databinding.FragmentColorPickerBinding
import com.therxmv.telegramthemer.ui.base.BaseBindingBottomSheetFragment
import top.defaults.colorpicker.AlphaSliderView
import top.defaults.colorpicker.BrightnessSliderView
import top.defaults.colorpicker.ColorWheelView

class ColorPickerFragment : BaseBindingBottomSheetFragment<FragmentColorPickerBinding>() {
// TODO implement alpha slider and input fields

    companion object {
        private const val DEFAULT_DIM = 0.3f
        private const val TRANSPARENCY_VALUE = 0.3f

        fun createInstance() = ColorPickerFragment().apply {
            // TODO put default color as argument
        }
    }

    private var dialog: BottomSheetDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTouchListeners()
        subscribeOnColorChanges()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = onCreateView(inflater, container, FragmentColorPickerBinding::inflate)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog?.behavior?.isDraggable = false
        dialog?.window?.setDimAmount(DEFAULT_DIM)

        return dialog!!
    }

    private fun subscribeOnColorChanges() {
        binding.colorPicker.subscribe { color, _, _ ->
            (requireActivity() as? ColorPickerSubscriber)?.onColorChanged(color)
        }
    }

    private fun setUpTouchListeners() {
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
                dialog?.window?.attributes?.alpha = TRANSPARENCY_VALUE
                dialog?.window?.setDimAmount(0f)
            }

            MotionEvent.ACTION_UP -> {
                dialog?.window?.attributes?.alpha = 1f
                dialog?.window?.setDimAmount(DEFAULT_DIM)
            }
        }

        true
    }
}