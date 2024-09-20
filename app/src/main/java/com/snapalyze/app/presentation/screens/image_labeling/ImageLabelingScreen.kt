package com.snapalyze.app.presentation.screens.image_labeling


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.label.ImageLabel
import com.snapalyze.app.R
import com.snapalyze.app.analyzer.ImageLabelingAnalyzer
import com.snapalyze.app.presentation.common.components.CameraView


object ImageLabelingScreen : Screen {


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val localNavigator = LocalNavigator.currentOrThrow

        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        navigationIcon = {
                            IconButton(onClick = {
                                localNavigator.pop()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "close"
                                )
                            }
                        },
                        title = { Text(text = stringResource(id = R.string.image_labeling_detection_title)) })
                },
                contentColor = MaterialTheme.colorScheme.primary,
                content = { padding ->
                    ImageLabelingScreenContent()
                }
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImageLabelingScreenContent() {
    val context = LocalContext.current
    val cameraPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)

    PermissionRequired(
        permissionState = cameraPermissionState,
        permissionNotGrantedContent = {
            LaunchedEffect(Unit) {
                cameraPermissionState.launchPermissionRequest()
            }
        },
        permissionNotAvailableContent = {
            Column {
                Toast.makeText(context, "Permission denied.", Toast.LENGTH_LONG).show()
            }
        }) {
        ScanSurface()
    }
}

@Composable
fun ScanSurface() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val imageLabels = remember { mutableStateListOf<ImageLabel>() }

    Box(modifier = Modifier.fillMaxSize()) {
        CameraView(
            context = context,
            lifecycleOwner = lifecycleOwner,
            analyzer = ImageLabelingAnalyzer {
                imageLabels.clear()
                imageLabels.addAll(it)
            }
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.image_labeling_detection_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }

            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
            ) {
                Text(
                    text = imageLabels.joinToString(separator = "\n") { it.text },
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }
    }
}