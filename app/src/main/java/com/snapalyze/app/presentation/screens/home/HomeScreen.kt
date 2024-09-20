package com.snapalyze.app.presentation.screens.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.snapalyze.app.R
import com.snapalyze.app.presentation.common.components.ImageCard
import com.snapalyze.app.presentation.screens.barcode_scanning.BarcodeScanningScreen
import com.snapalyze.app.presentation.screens.image_labeling.ImageLabelingScreen
import com.snapalyze.app.presentation.screens.text_recognition.TextRecognitionScreen

object HomeScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")
                            }
                        },
                        title = { Text(text = stringResource(id = R.string.app_name)) })
                },
                contentColor = MaterialTheme.colorScheme.primary,
                content = {
                    LazyColumn(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        contentPadding = it
                    ) {
                        item {
                            ImageCard(
                                title = stringResource(id = R.string.barcode_detection_title),
                                description = stringResource(id = R.string.barcode_detection_description),
                                imageUrl = stringResource(id = R.string.barcode_detection_url),
                                onCardClick = { navigator.push(BarcodeScanningScreen) }
                            )
                        }

                        item {
                            ImageCard(
                                title = stringResource(id = R.string.text_recognition_title),
                                description = stringResource(id = R.string.text_recognition_description),
                                imageUrl = stringResource(id = R.string.text_recognition_url),
                                onCardClick = { navigator.push(TextRecognitionScreen) }
                            )
                        }

                        item {
                            ImageCard(
                                title = stringResource(id = R.string.image_labeling_detection_title),
                                description = stringResource(id = R.string.image_labeling_detection_description),
                                imageUrl = stringResource(id = R.string.image_labeling_detection_url),
                                onCardClick = { navigator.push(ImageLabelingScreen) }
                            )
                        }

                    }
                }
            )
        }
    }
}