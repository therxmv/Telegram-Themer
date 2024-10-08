package com.therxmv.telegramthemer.ui.v2

import android.os.Bundle
import com.therxmv.telegramthemer.databinding.ActivityMainV2Binding
import com.therxmv.telegramthemer.ui.base.BaseBindingActivity

class MainActivityV2 : BaseBindingActivity<ActivityMainV2Binding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainV2Binding::inflate)
        // TODO set up toolbar
//        ColorPickerFragment.createInstance().show(supportFragmentManager, "ColorPickerFragment")
    }
}