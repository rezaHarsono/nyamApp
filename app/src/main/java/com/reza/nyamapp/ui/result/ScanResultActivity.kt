package com.reza.nyamapp.ui.result

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.reza.nyamapp.ViewModelFactory
import com.reza.nyamapp.data.ScanResult
import com.reza.nyamapp.databinding.ActivityScanResultBinding
import com.reza.nyamapp.ui.camera.CameraActivity
import com.reza.nyamapp.ui.home.HomeActivity
import com.reza.nyamapp.utils.CALORIES
import java.io.File
import java.io.FileOutputStream

class ScanResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanResultBinding

    private lateinit var viewModel: ScanResultViewModel

    private lateinit var imageUri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(ScanResultViewModel::class.java)

        imageUri = intent.getStringExtra(EXTRA_IMAGE_URI).toString()
        Glide.with(this)
            .load(Uri.parse(imageUri))
            .into(binding.ivResult)

        binding.btnRetry.setOnClickListener {
            saveResult()
            viewModel.loadCaloriesToday(System.currentTimeMillis().toString())

            viewModel.totalCalories.observe(this) { totalKalori ->
                val intent = Intent(this, CameraActivity::class.java)
                intent.putExtra(CALORIES, totalKalori.toString())
                startActivity(intent)
            }

        }

        binding.btnFinish.setOnClickListener {
            saveResult()
            viewModel.loadCaloriesToday(System.currentTimeMillis().toString())

            viewModel.totalCalories.observe(this) { totalKalori ->
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra(CALORIES, totalKalori.toString())
                startActivity(intent)
            }

            viewModel.saved.observe(this) { event ->
                val saved = event.getContentIfNotHandled()
                if (saved == true) {
                    Toast.makeText(this, "Scan Result Saved", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun saveResult() {
        val foodName = "Test"
        val kalori = 100.0
        val lemak = 20.0
        val karbo = 30.0
        val protein = 40.0
        val zatBesi = 5.0
        val kalsium = 6.0

        val result = ScanResult(
            image = copyUriToInternalStorage(this, imageUri.toUri()).toString(),
            foodName = foodName,
            kalori = kalori,
            lemak = lemak,
            karbo = karbo,
            protein = protein,
            zatBesi = zatBesi,
            kalsium = kalsium,
            resultAddedInMillis = System.currentTimeMillis()
        )

        viewModel.insertResult(result)
    }

    fun copyUriToInternalStorage(context: Context, sourceUri: Uri): Uri {
        val inputStream = context.contentResolver.openInputStream(sourceUri)
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)
        val outputStream = FileOutputStream(file)

        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()

        return Uri.fromFile(file)
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}