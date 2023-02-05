package com.yourssohail.jetpackworkmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yourssohail.jetpackworkmanager.data.WorkerRepository

class WorkerFactory(private val workerRepository: WorkerRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WorkerViewModel(workerRepository ) as T
    }
}