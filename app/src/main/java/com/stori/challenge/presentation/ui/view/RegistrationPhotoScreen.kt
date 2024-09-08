package com.stori.challenge.presentation.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.rememberAsyncImagePainter
import com.stori.challenge.R
import com.stori.challenge.presentation.ui.component.StoriButton
import com.stori.challenge.presentation.ui.component.StoriTopBar
import com.stori.challenge.presentation.ui.intent.RegistrationPhotoIntent
import com.stori.challenge.presentation.ui.state.RegistrationPhotoState
import com.stori.challenge.presentation.ui.viewmodel.RegistrationPhotoViewModel
import com.stori.challenge.presentation.utils.FileUtils
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegistrationPhotoScreen(
    viewModel: RegistrationPhotoViewModel = hiltViewModel(),
    goToHomeScreen: () -> Unit,
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.goToHomeScreen.collectLatest {
                goToHomeScreen()
            }
        }
    }

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.showError.collectLatest {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    val handleIntent = { intent: RegistrationPhotoIntent ->
        when(intent) {
            is RegistrationPhotoIntent.OnNavClicked -> goBack()
            else -> viewModel.handleIntent(intent)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Scaffold(
            topBar = {
                StoriTopBar(
                    showNavButton = true,
                    onNavClicked = { handleIntent(RegistrationPhotoIntent.OnNavClicked) }
                )
            }
        ) {
            RegistrationPhotoScreenContent(it, state, handleIntent)
        }

        if (state.showSuccess) SuccessScreen()
    }
}

@Composable
fun RegistrationPhotoScreenContent(
    paddingValues: PaddingValues,
    state: RegistrationPhotoState,
    handleIntent: (RegistrationPhotoIntent) -> Unit,
) {
    val context = LocalContext.current
    val uri = FileUtils.createUri(context)

    val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ){
        handleIntent(RegistrationPhotoIntent.OnTakePictureClicked(uri))
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ){
        if (it){
            Toast.makeText(context, "Permissions granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        }else{
            Toast.makeText(context, "Permissions denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = dimensionResource(id = R.dimen.padding_xl))
            .padding(top = dimensionResource(id = R.dimen.padding_xl))
    ) {
        Text(
            text = stringResource(id = R.string.registration_form_title),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_l)),
            text = stringResource(id = R.string.registration_photo_description),
            style = MaterialTheme.typography.bodyMedium
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.padding_xl)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.registration_image_size))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius))),
                painter = rememberAsyncImagePainter(
                    if (state.photo.path?.isNotEmpty() == true) state.photo else R.drawable.pic_take_picture
                ),
                contentDescription = null
            )
        }
        StoriButton(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_xl)),
            onClick = {
                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                    cameraLauncher.launch(uri)
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            textId = R.string.registration_take_picture_button,
            enabled = !state.isLoading
        )
        Spacer(modifier = Modifier.weight(1f))
        StoriButton(
            onClick = { handleIntent(RegistrationPhotoIntent.OnRegisterClicked) },
            textId = R.string.registration_register_button,
            enabled = state.isRegisterButtonEnabled,
            isLoading = state.isLoading
        )
    }
}
