package com.yourssohail.jetpackworkmanager

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourssohail.jetpackworkmanager.data.WorkerRepository
import com.yourssohail.jetpackworkmanager.ui.WorkerFactory
import com.yourssohail.jetpackworkmanager.ui.WorkerViewModel
import com.yourssohail.jetpackworkmanager.ui.theme.JetpackWorkmanagerTheme
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackWorkmanagerTheme {
                WorkManagerScreen()
            }
        }
    }
}

@Composable
fun WorkManagerScreen() {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val workerFactory = WorkerFactory(WorkerRepository(LocalContext.current))
    val workerViewModel: WorkerViewModel = viewModel(factory = workerFactory)
    Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        ImagePickerCard {
            imageUri = it
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if(imageUri != null) {
                Button(onClick = {
                    workerViewModel.compressImage(imageUri!!)
                }) {
                    Text(text = "Compress")
                }
            }

        }

    }
}

@Composable
fun ImagePickerCard(onImageLoaded: (uri: Uri) -> Unit) {
    val context = LocalContext.current
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
            if(imageUri !== null) {
                onImageLoaded(imageUri!!)
            }
        }
    if(bitmap.value === null) {
        Card(
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
                .background(Color.Gray)
                .clickable {
                    launcher.launch("image/*")
                },
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Upload Image")
            }
        }
    }

    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)
        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, it)
            bitmap.value = ImageDecoder.decodeBitmap(source)
        }
        bitmap.value?.let { btm ->
            Image(
                bitmap = btm.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(300.dp)
            )
        }
    }
}