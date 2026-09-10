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
import com.therxmv.telegramthemer.domain.model.TemplateCapabilities
import com.therxmv.telegramthemer.domain.model.TemplateStyle
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.ui.base.BaseBindingBottomSheetFragment
import com.therxmv.telegramthemer.ui.extensions.isMonetAvailable
import com.therxmv.telegramthemer.ui.extensions.toVisibility
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MoreOptionsBottomSheetFragment : BaseBindingBottomSheetFragment<FragmentMoreOptionsBinding>() {

    companion object {
        private const val CURRENT_STATE = "CurrentState"
        private const val AVAILABLE_STYLES = "AvailableStyles"
        private const val CAPABILITIES_BY_STYLE = "CapabilitiesByStyle"

        fun createInstance(
            currentState: ThemeState,
            styles: List<TemplateStyle>,
            capabilitiesByStyle: Map<String, TemplateCapabilities>,
        ) = MoreOptionsBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putString(CURRENT_STATE, Json.encodeToString(currentState))
                putString(AVAILABLE_STYLES, Json.encodeToString(styles))
                putString(CAPABILITIES_BY_STYLE, Json.encodeToString(capabilitiesByStyle))
            }
        }
    }

    private var themeState: ThemeState? = null
    private var availableStyles: List<TemplateStyle> = emptyList()
    private var capabilitiesByStyle: Map<String, TemplateCapabilities> = emptyMap()

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
        availableStyles = arguments?.getString(AVAILABLE_STYLES)?.let {
            Json.decodeFromString<List<TemplateStyle>>(it)
        }.orEmpty()
        capabilitiesByStyle = arguments?.getString(CAPABILITIES_BY_STYLE)?.let {
            Json.decodeFromString<Map<String, TemplateCapabilities>>(it)
        }.orEmpty()

        themeState?.let {
            setUpDropDown(it)
            updateCheckBoxes(it, capabilitiesFor(it.style))
            initCheckBoxListeners()
        }
    }

    private fun capabilitiesFor(styleId: String) = capabilitiesByStyle[styleId] ?: TemplateCapabilities()

    private fun setUpDropDown(themeState: ThemeState) {
        val currentLabel = availableStyles.firstOrNull { it.id == themeState.style }?.label
            ?: themeState.style
        binding.selectorItems.setText(currentLabel)
        val labels = availableStyles.map { it.label }
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, labels)
        binding.selectorItems.setAdapter(arrayAdapter)

        binding.selectorItems.setOnDismissListener {
            binding.styleSelector.clearFocus()
        }
        binding.selectorItems.setOnItemClickListener { _, _, _, _ ->
            val label = binding.styleSelector.editText?.text.toString()
            val style = availableStyles.firstOrNull { it.label == label }
            style?.let { onStyleSelected(it) }
        }
    }

    /**
     * A style switch can change which toggles are even valid (see
     * [TemplateCapabilities]) - clamp the local state and refresh the
     * checkboxes immediately, rather than waiting for the round trip through
     * [notifyAboutChanges] to fix it up (the presenter clamps too, but this
     * sheet keeps its own local copy and isn't notified back of that fix).
     */
    private fun onStyleSelected(style: TemplateStyle) {
        val capabilities = capabilitiesFor(style.id)
        val newState = themeState?.copy(style = style.id)?.let {
            val isDark = capabilities.resolveIsDark(it.isDark)
            it.copy(
                isDark = isDark,
                isAmoled = it.isAmoled && isDark,
                isGradient = it.isGradient && capabilities.hasGradient,
            )
        } ?: return

        themeState = newState
        updateCheckBoxes(newState, capabilities)
        notifyAboutChanges()
    }

    /**
     * Either variant could be the one missing - a dark-only style must
     * resolve to dark, not just fall back to light like a light-only one.
     * Mirrors [com.therxmv.telegramthemer.ui.editor.ThemeEditorPresenter.clampToCapabilities],
     * which is still the source of truth for what actually gets persisted.
     */
    private fun TemplateCapabilities.resolveIsDark(currentIsDark: Boolean): Boolean = when {
        currentIsDark && hasDark -> true
        !currentIsDark && hasLight -> false
        else -> hasDark
    }

    private fun updateCheckBoxes(themeState: ThemeState, capabilities: TemplateCapabilities) {
        with(themeState) {
            binding.darkCheckBox.isChecked = isDark
            // Only shown when it's an actual choice - a style with just one
            // variant has nothing to toggle, isDark is simply fixed to it.
            binding.darkCheckBox.visibility = (capabilities.hasLight && capabilities.hasDark).toVisibility()

            // Amoled is only a variant of dark mode (no separate template file),
            // so it's selectable whenever dark is reachable at all, toggle or not.
            binding.amoledCheckBox.isChecked = isAmoled
            binding.amoledCheckBox.visibility = capabilities.hasDark.toVisibility()

            binding.gradientCheckBox.isChecked = isGradient
            binding.gradientCheckBox.visibility = capabilities.hasGradient.toVisibility()

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
