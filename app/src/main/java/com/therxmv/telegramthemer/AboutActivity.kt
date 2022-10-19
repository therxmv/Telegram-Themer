package com.therxmv.telegramthemer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.therxmv.telegramthemer.databinding.ActivityAboutBinding


class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.aboutToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tgButton = binding.aboutBtnTg//: Button = findViewById(R.id.about_btnTg)
        val gitButton = binding.aboutBtnGit//: Button = findViewById(R.id.about_btnGit)

        tgButton.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/therxmv_channel"))
            startActivity(browserIntent)
        }

        gitButton.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/therxmv/Telegram-Themer"))
            startActivity(browserIntent)
        }
    }
}