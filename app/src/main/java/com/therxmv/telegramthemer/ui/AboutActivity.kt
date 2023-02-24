package com.therxmv.telegramthemer.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.therxmv.telegramthemer.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private val binding: ActivityAboutBinding by lazy { ActivityAboutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.aboutToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.aboutBtnTg.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/therxmv_channel"))
            startActivity(browserIntent)
        }

        binding.aboutBtnGit.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/therxmv/Telegram-Themer"))
            startActivity(browserIntent)
        }
    }
}