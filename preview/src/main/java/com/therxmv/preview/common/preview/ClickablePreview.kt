package com.therxmv.preview.common.preview

import android.view.View
import com.therxmv.preview.utils.AtthemePreviewKeys

interface ClickablePreview {
    fun setColorPickerAction(openColorPicker: View.(AtthemePreviewKeys) -> Unit)
}