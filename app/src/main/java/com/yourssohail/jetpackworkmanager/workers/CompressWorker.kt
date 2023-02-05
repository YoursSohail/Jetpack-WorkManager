package com.yourssohail.jetpackworkmanager.workers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File

private const val TAG = "CompressWorker"

class CompressWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val resourceUri = Uri.parse(inputData.getString(KEY_IMAGE_URI))
        Log.d(TAG, resourceUri.toString())
        makeStatusNotification(
            "Compressing image",
            applicationContext
        )
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val resolver = applicationContext.contentResolver
                // Compress the image using the Bitmap.compress method
                val image = BitmapFactory.decodeStream(resolver.openInputStream(resourceUri))
                val byteArrayOutputStream = ByteArrayOutputStream()
                image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()

                // Write the compressed image to a file on the device
                val file = File(applicationContext.filesDir, "compressed_image.jpeg")
                file.writeBytes(byteArray)

                // Pass the file URI to the next worker
                val outputData =
                    Data.Builder().putString(COMPRESSED_IMAGE_URI, file.toURI().toString()).build()

                Log.d(TAG, byteArray.toString())
                Result.success(outputData)
            } catch (throwable: Throwable) {
                Log.e(TAG, throwable.message.toString())
                Result.failure()
            }
        }
    }

}