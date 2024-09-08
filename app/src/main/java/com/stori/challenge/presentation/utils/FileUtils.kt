package com.stori.challenge.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

object FileUtils {

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        return File.createTempFile(
            imageFileName,
            ".jpg",
            context.externalCacheDir
        )
    }

    fun createUri(context: Context): Uri {
        val file = createImageFile(context)
        return FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            context.packageName + ".provider", file
        )
    }
}