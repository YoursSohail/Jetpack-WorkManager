package com.yourssohail.jetpackworkmanager.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.yourssohail.jetpackworkmanager.data.IWorkerRepository

class WorkerViewModel(private val workerRepository: IWorkerRepository) : ViewModel() {

    fun compressImage(uri: Uri) {
        workerRepository.compressImage(uri)
    }
}