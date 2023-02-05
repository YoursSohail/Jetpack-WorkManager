package com.yourssohail.jetpackworkmanager.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.yourssohail.jetpackworkmanager.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "SaveImageWorker"

class SaveImageWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        makeStatusNotification(
            "Saving image",
            applicationContext
        )

        return withContext(Dispatchers.IO) {
            return@withContext try {
                val fileUri = inputData.getString(COMPRESSED_IMAGE_URI)
                val file = File(URI.create(fileUri))

                // Read the compressed image from the file
                val byteArray = file.readBytes()

                // Write the byte array to a file on the device
                val externalFile =
                    File(applicationContext.getExternalFilesDir(null), "compressed_image.jpeg")
                Log.d(TAG,externalFile.path)
                externalFile.writeBytes(byteArray)
                Result.success()
            } catch (exception: Exception) {
                Log.e(TAG,exception.message.toString())
                Result.failure()
            }
        }

    }
}