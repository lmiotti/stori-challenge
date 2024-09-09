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

    private const val FILE_DATE_FORMAT = "yyyyMMdd_HHmmss"
    private const val FILE_EXTENSION = ".jpg"

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat(FILE_DATE_FORMAT).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        return File.createTempFile(
            imageFileName,
            FILE_EXTENSION,
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