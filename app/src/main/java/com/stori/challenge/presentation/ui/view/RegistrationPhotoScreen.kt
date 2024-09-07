package com.stori.challenge.presentation.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.rememberAsyncImagePainter
import com.stori.challenge.R
import com.stori.challenge.presentation.ui.component.StoriButton
import com.stori.challenge.presentation.ui.component.StoriTopBar
import com.stori.challenge.presentation.ui.intent.RegistrationFormIntent
import com.stori.challenge.presentation.ui.intent.RegistrationPhotoIntent
import com.stori.challenge.presentation.ui.viewmodel.RegistrationPhotoViewModel
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun RegistrationPhotoScreen(
    viewModel: RegistrationPhotoViewModel = hiltViewModel(),
    goToHomeScreen: () -> Unit,
    goBack: () -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.goToHomeScreen.collectLatest {
                goToHomeScreen()
            }
        }
    }

    val handleIntent = { intent: RegistrationPhotoIntent ->
        when(intent) {
            is RegistrationPhotoIntent.OnNavClicked -> goBack()
            else -> viewModel.handleIntent(intent)
        }
    }

    Scaffold(
        topBar = {
            StoriTopBar(
                showNavButton = true,
                onNavClicked = { handleIntent(RegistrationPhotoIntent.OnNavClicked) }
            )
        }
    ) {
        RegistrationPhotoScreenContent(it, handleIntent, viewModel)
    }
}

@Composable
fun RegistrationPhotoScreenContent(
    paddingValues: PaddingValues,
    handleIntent: (RegistrationPhotoIntent) -> Unit,
    viewModel: RegistrationPhotoViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ){
        handleIntent(RegistrationPhotoIntent.OnTakePictureClicked(uri))
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ){
        if (it != null){
            //viewModel.showToast(context,"Permiso Concedido")
            cameraLauncher.launch(uri)
        }else{
            //viewModel.showToast(context,"Permiso Denegado")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.registration_form_title),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            modifier = Modifier.padding(top = 15.dp),
            text = stringResource(id = R.string.registration_photo_description),
            style = MaterialTheme.typography.bodyMedium
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(10.dp)),
                painter = rememberAsyncImagePainter(
                    if (state.photo.path?.isNotEmpty() == true) state.photo else R.drawable.pic_take_picture
                ),
                contentDescription = null
            )
        }
        StoriButton(
            modifier = Modifier.padding(top = 20.dp),
            onClick = {
                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                    cameraLauncher.launch(uri)
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            textId = R.string.registration_take_picture_button
        )
        Spacer(modifier = Modifier.weight(1f))
        StoriButton(
            onClick = { handleIntent(RegistrationPhotoIntent.OnRegisterClicked) },
            textId = R.string.registration_register_button,
            enabled = state.isRegisterButtonEnabled
        )
    }
}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}
