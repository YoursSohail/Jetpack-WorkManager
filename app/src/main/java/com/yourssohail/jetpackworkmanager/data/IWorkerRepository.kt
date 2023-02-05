package com.yourssohail.jetpackworkmanager.data

import android.net.Uri

interface IWorkerRepository {
    fun compressImage(uri: Uri)
}