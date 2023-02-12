package com.yourssohail.jetpackworkmanager.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.yourssohail.jetpackworkmanager.data.IWorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkerViewModel @Inject constructor(private val workerRepository: IWorkerRepository) : ViewModel() {

    fun compressImage(uri: Uri,context: Context) {
        workerRepository.compressImage(uri,context)
    }
}