package com.alejandrorios.peakstest.utils

import android.content.ClipData
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.DATE
import kotlin.random.Random

/**
 * Simple delegate to handle viewBinding process
 *
 * Reference: https://zhuinden.medium.com/simple-one-liner-viewbinding-in-fragments-and-activities-with-kotlin-961430c6c07c
 */
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

fun Context.showToast(@StringRes message: Int, length: Int = LENGTH_LONG) {
    Toast.makeText(this, this.getString(message), length).show()
}

fun View.beginDrag(dataToDrag: ClipData, shadowPeaks: PeaksDragShadowBuilder) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        @Suppress("DEPRECATION")
        this.startDrag(dataToDrag, shadowPeaks, this, 0)
    } else {
        this.startDragAndDrop(dataToDrag, shadowPeaks, this, 0)
    }
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)

    return formatter.format(this)
}

fun String.toDate(format: String, locale: Locale = Locale.getDefault()): Date? {
    val formatter = SimpleDateFormat(format, locale)

    return formatter.parse(this)
}

fun Date?.isMoreThanAWeek(): Boolean = this?.let {
    val today = Calendar.getInstance().time
    val c = Calendar.getInstance().apply {
        time = it
        add(DATE, 7)
    }

    c.time.compareTo(today) < 0
} ?: false

fun getRandomColor(): Int = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
