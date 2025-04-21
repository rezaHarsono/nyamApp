package com.reza.nyamapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.reza.nyamapp.ViewModelFactory
import com.reza.nyamapp.databinding.ActivityHomeBinding
import com.reza.nyamapp.ui.SettingActivity
import com.reza.nyamapp.ui.camera.CameraActivity
import com.reza.nyamapp.ui.history.HistoryActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        binding.cardSetting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        binding.cardScan.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        binding.cardHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        viewModel.getCaloriesToday(System.currentTimeMillis().toString()).observe(this) { calories ->
            binding.tvCurrentCallorie.text = (calories ?: 0).toString()
        }

    }
}