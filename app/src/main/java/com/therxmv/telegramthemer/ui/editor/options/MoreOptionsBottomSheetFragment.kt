package com.therxmv.telegramthemer.ui.editor.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.databinding.FragmentMoreOptionsBinding
import com.therxmv.telegramthemer.domain.model.Styles
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.ui.base.BaseBindingBottomSheetFragment
import com.therxmv.telegramthemer.ui.extensions.isMonetAvailable
import com.therxmv.telegramthemer.ui.extensions.toVisibility
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MoreOptionsBottomSheetFragment : BaseBindingBottomSheetFragment<FragmentMoreOptionsBinding>() {

    companion object {
        private const val CURRENT_STATE = "CurrentState"

        fun createInstance(currentState: ThemeState) = MoreOptionsBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putString(CURRENT_STATE, Json.encodeToString(currentState))
            }
        }
    }

    private var themeState: ThemeState? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = onCreateView(inflater, container, FragmentMoreOptionsBinding::inflate)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        themeState = arguments?.getString(CURRENT_STATE)?.let {
            Json.decodeFromString<ThemeState>(it)
        }
        themeState?.let {
            setUpDropDown(it)
            updateCheckBoxes(it)
            initCheckBoxListeners()
        }
    }

    private fun setUpDropDown(themeState: ThemeState) {
        binding.selectorItems.setText(themeState.style.label)
        val styles = Styles.entries.map { it.label }
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, styles)
        binding.selectorItems.setAdapter(arrayAdapter)

        binding.selectorItems.setOnDismissListener {
            binding.styleSelector.clearFocus()
        }
        binding.selectorItems.setOnItemClickListener { _, _, _, _ ->
            val label = binding.styleSelector.editText?.text.toString()
            val style = Styles.entries.firstOrNull { it.label == label }
            style?.let {
                this.themeState = this.themeState?.copy(
                    style = it,
                )
                notifyAboutChanges()
            }
        }
    }

    private fun updateCheckBoxes(themeState: ThemeState) {
        with(themeState) {
            binding.darkCheckBox.isChecked = isDark
            binding.amoledCheckBox.isChecked = isAmoled
            binding.gradientCheckBox.isChecked = isGradient

            binding.monetCheckBox.isChecked = isMonet
            binding.monetCheckBox.visibility = isMonetAvailable().toVisibility()
        }
    }

    private fun initCheckBoxListeners() {
        binding.monetCheckBox.setOnClickListener {
            it as CheckBox

            if (it.isChecked) setMonetColor()

            themeState = themeState?.copy(
                isMonet = it.isChecked,
            )
            notifyAboutChanges()
        }

        binding.gradientCheckBox.setOnClickListener {
            it as CheckBox
            themeState = themeState?.copy(
                isGradient = it.isChecked,
            )
            notifyAboutChanges()
        }

        binding.amoledCheckBox.setOnClickListener {
            it as CheckBox

            // Toggle on dark check box as well
            val isAmoled = it.isChecked
            val isDark = binding.darkCheckBox.isChecked
                .takeIf { isAmoled.not() } ?: true

            themeState = themeState?.copy(
                isDark = isDark,
                isAmoled = isAmoled,
            )
            binding.darkCheckBox.isChecked = isDark

            if (themeState?.isMonet == true) setMonetColor()

            notifyAboutChanges()
        }

        binding.darkCheckBox.setOnClickListener {
            it as CheckBox

            // Toggle off amoled check box as well
            val isDark = it.isChecked
            val isAmoled = binding.amoledCheckBox.isChecked
                .takeIf { isDark } ?: false

            themeState = themeState?.copy(
                isDark = isDark,
                isAmoled = isAmoled,
            )
            binding.amoledCheckBox.isChecked = isAmoled

            if (themeState?.isMonet == true) setMonetColor()

            notifyAboutChanges()
        }
    }

    private fun setMonetColor() {
        val color = if (themeState?.isDark == true) {
            R.color.theme_accent1_200
        } else {
            R.color.theme_accent1_500
        }
        themeState = themeState?.copy(
            accent = ContextCompat.getColor(requireContext(), color),
        )
    }

    private fun notifyAboutChanges() {
        themeState?.let {
            (requireActivity() as? MoreOptionsSubscriber)?.onPropertyChange(it)
        }
    }
}