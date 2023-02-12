package com.yourssohail.jetpackworkmanager.data

import android.content.Context
import android.net.Uri

interface IWorkerRepository {
    fun compressImage(uri: Uri,context: Context)
}