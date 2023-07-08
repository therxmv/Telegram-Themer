package com.therxmv.telegramthemer.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.therxmv.telegramthemer.BuildConfig
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private val binding: ActivityAboutBinding by lazy { ActivityAboutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.aboutToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initListeners()

        binding.aboutTitle.text = getString(R.string.about_title, BuildConfig.VERSION_NAME)
    }

    private fun initListeners() {
        binding.aboutBtnTg.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/therxmv_channel"))

            try {
                startActivity(browserIntent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
            }
        }

        binding.aboutBtnGit.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/therxmv/Telegram-Themer"))

            try {
                startActivity(browserIntent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
            }
        }
    }
}