package com.example.a515learnandroid1

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage

class GalleryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryScreen()
        }
    }
}

@Composable
fun GalleryScreen() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = androidx.compose.ui.Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Selected Image",
                modifier = androidx.compose.ui.Modifier.size(300.dp)
            )
        } else {
            // Placeholder can be anything; reusing profile image from previous logic
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile placeholder",
                modifier = androidx.compose.ui.Modifier.size(300.dp)
            )
        }

        Spacer(modifier = androidx.compose.ui.Modifier.height(32.dp))

        Button(onClick = {
            val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                android.Manifest.permission.READ_MEDIA_IMAGES
            } else {
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            }

            val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                galleryLauncher.launch("image/*")
            } else {
                permissionLauncher.launch(permission)
            }
        }) {
            Text("เลือกรูปภาพ")
        }
    }
}
