package com.example.eisapp.classifier

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class ImageClassifier(private val context: Context) {

    private val modelName = "mobilenetv2.tflite"
    private val inputSize = 224
    private val interpreter: Interpreter
    private val labels: List<String>

    init {
        interpreter = Interpreter(loadModelFile())
        labels = loadLabels()
    }

    private fun loadModelFile(): MappedByteBuffer {
        return try {
            val fileDescriptor = context.assets.openFd(modelName)
            FileInputStream(fileDescriptor.fileDescriptor).use { inputStream ->
                val fileChannel = inputStream.channel
                val startOffset = fileDescriptor.startOffset
                val declaredLength = fileDescriptor.declaredLength
                fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
            }
        } catch (e: Exception) {
            Log.e("ImageClassifier", "Error loading model from assets: $modelName", e)
            throw RuntimeException("Error loading model from assets: $modelName", e)
        }
    }

    private fun loadLabels(): List<String> {
        return context.assets.open("labels.txt").bufferedReader().useLines { lines ->
            lines.toList()
        }
    }

    fun classify(bitmap: Bitmap): String {
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)
        val byteBuffer = convertBitmapToByteBuffer(resizedBitmap)

        val output = Array(1) { FloatArray(labels.size) }
        interpreter.run(byteBuffer, output)

        val maxIndex = output[0].indices.maxByOrNull { output[0][it] } ?: -1
        val confidence = output[0][maxIndex]

        val label = if (maxIndex in labels.indices) labels[maxIndex] else "Unknown"

        return "Prediction: $label\nConfidence: ${(confidence * 100).toInt()}%"
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * inputSize * inputSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(inputSize * inputSize)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        for (pixel in intValues) {
            val r = ((pixel shr 16) and 0xFF) / 255.0f
            val g = ((pixel shr 8) and 0xFF) / 255.0f
            val b = (pixel and 0xFF) / 255.0f

            byteBuffer.putFloat(r)
            byteBuffer.putFloat(g)
            byteBuffer.putFloat(b)
        }

        byteBuffer.rewind()

        return byteBuffer
    }

    // Helper method to check if model file exists in assets
    fun testAssetExists(): Boolean {
        return try {
            context.assets.open(modelName).close()
            true
        } catch (e: Exception) {
            Log.e("ImageClassifier", "Model file not found in assets: $modelName", e)
            false
        }
    }
}
