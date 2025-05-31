package com.example.eisapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.eisapp.classifier.ImageClassifier

class MainActivity : AppCompatActivity() {
    private lateinit var imageClassifier: ImageClassifier
    private lateinit var imageView: ImageView
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        imageClassifier = ImageClassifier(this)
        imageView = findViewById(R.id.imageView)
        resultTextView = findViewById(R.id.resultTextView)

        val loadImageFromAssets = findViewById<Button>(R.id.loadImageFromAssets)
        val loadImageFromGallery = findViewById<Button>(R.id.loadImageFromGallery)

        // Load from assets
        loadImageFromAssets.setOnClickListener {
            try {
                val inputStream = assets.open("sample.jpg") // Make sure this exists
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()

                imageView.setImageBitmap(bitmap)
                resultTextView.text = imageClassifier.classify(bitmap)
            } catch (e: Exception) {
                Toast.makeText(this, "Error loading image from assets", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        // Load from gallery
        loadImageFromGallery.setOnClickListener {
            pickImageLauncher.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
        }
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val imageUri: Uri? = result.data!!.data
            if (imageUri != null) {
                val bitmap = uriToBitmap(imageUri)
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap)
                    resultTextView.text = imageClassifier.classify(bitmap)
                } else {
                    Toast.makeText(this, "Failed to decode image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
