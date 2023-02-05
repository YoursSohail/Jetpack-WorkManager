package com.yourssohail.jetpackworkmanager.data

import android.content.Context
import android.net.Uri
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.yourssohail.jetpackworkmanager.workers.CompressWorker
import com.yourssohail.jetpackworkmanager.workers.KEY_IMAGE_URI
import com.yourssohail.jetpackworkmanager.workers.SaveImageWorker

class WorkerRepository(context: Context): IWorkerRepository {

    private val workManager = WorkManager.getInstance(context)
    override fun compressImage(uri: Uri) {
        val compressWorker = OneTimeWorkRequestBuilder<CompressWorker>()
            .setInputData(createInputDataForWorkRequest(uri))
            .build()
        val saveImageWorker = OneTimeWorkRequestBuilder<SaveImageWorker>().build()
        workManager.beginWith(compressWorker)
            .then(saveImageWorker)
            .enqueue()
    }

    private fun createInputDataForWorkRequest(imageUri: Uri): Data {
        val builder = Data.Builder()
        builder.putString(KEY_IMAGE_URI, imageUri.toString())
        return builder.build()
    }
}