package com.breno.instagram.common.util

import android.app.Activity
import com.breno.instagram.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

object Files {

    //ano-mes-dia-hora-minuto-segundo-centesimo
    private const val FILENAME_FORMAT = "yyyy-mm-dd-HH-mm-ss-SSS"
    private const val JPG = ".jpg"

    fun generateFile(activity: Activity): File {
        activity.run {
            val outputDir = externalMediaDirs.firstOrNull()?.let {
                File(it, getString(R.string.app_name)).apply {
                    mkdirs()
                }
            }?.run {
                takeIf { exists() }?.run { this } ?: filesDir
            } ?: filesDir

            return File(
                outputDir,
                SimpleDateFormat(
                    FILENAME_FORMAT,
                    Locale.getDefault()
                ).format(System.currentTimeMillis()) + JPG
            )
        }
    }
}