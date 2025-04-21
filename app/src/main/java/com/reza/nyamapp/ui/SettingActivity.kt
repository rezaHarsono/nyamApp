package com.reza.nyamapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reza.nyamapp.R
import com.reza.nyamapp.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}