package com.tehran.motivation.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.text.format.DateFormat
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.util.*


fun takeScreenshot(view: View): File? {
    val now = Date()
    DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
    return try {
        // image naming and path  to include sd card  appending name you choose for file
        val mPath: String =
            view.context.cacheDir.toString() + "/" + now + ".png"
        val bitmap: Bitmap = getBitmapFromView(view)
        File(mPath).apply {
            writeBitmap(bitmap, Bitmap.CompressFormat.PNG, 100)
        }
    } catch (e: Throwable) {
        // Several error may come out with file handling or DOM
        e.printStackTrace()
        null
    }
}

fun getBitmapFromView(view: View): Bitmap {
    val bitmap =
        Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}