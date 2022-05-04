package com.alejandrorios.peakstest.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.alejandrorios.peakstest.domain.model.RectangleWithPos
import com.alejandrorios.peakstest.utils.ARG_DATE_OF_CHANGES
import com.alejandrorios.peakstest.utils.ARG_RECTANGLES
import com.alejandrorios.peakstest.utils.DEFAULT_DATE_FORMAT
import com.alejandrorios.peakstest.utils.PREF_PACKAGE_NAME
import com.alejandrorios.peakstest.utils.toDate
import java.util.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedPreferencesHelper(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_PACKAGE_NAME, MODE_PRIVATE)

    fun persistRectanglesPositions(rectangleWithPos: List<RectangleWithPos>) {
        val rectangles = Json.encodeToString(rectangleWithPos)

        sharedPreferences.edit().putString(ARG_RECTANGLES, rectangles).apply()
    }

    fun persistDate(date: String) {
        sharedPreferences.edit().putString(ARG_DATE_OF_CHANGES, date).apply()
    }

    fun getRectanglesPositions(): List<RectangleWithPos>? {
        val list = sharedPreferences.getString(ARG_RECTANGLES, null)

        return list?.let { Json.decodeFromString<List<RectangleWithPos>>(it) }
    }

    fun getPersistedDate(): Date? = sharedPreferences.getString(ARG_DATE_OF_CHANGES, null)?.toDate(DEFAULT_DATE_FORMAT)
}
