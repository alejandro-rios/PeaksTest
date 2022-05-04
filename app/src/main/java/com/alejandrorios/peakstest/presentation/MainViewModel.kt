package com.alejandrorios.peakstest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.alejandrorios.peakstest.data.SharedPreferencesHelper
import com.alejandrorios.peakstest.data.utils.CallResult
import com.alejandrorios.peakstest.domain.model.RectangleWithPos
import com.alejandrorios.peakstest.domain.usecase.GetRectanglesUseCase
import com.alejandrorios.peakstest.utils.DEFAULT_DATE_FORMAT
import com.alejandrorios.peakstest.utils.isMoreThanAWeek
import com.alejandrorios.peakstest.utils.toString
import java.util.*

class MainViewModel(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val getRectanglesUseCase: GetRectanglesUseCase
) : ViewModel() {

    fun validateAndStart(): LiveData<CallResult<List<RectangleWithPos>>> = liveData {
        sharedPreferencesHelper.getPersistedDate()?.let { lastUpdate ->
            val savedData = sharedPreferencesHelper.getRectanglesPositions()

            if (savedData == null || lastUpdate.isMoreThanAWeek()) {
                emit(getRectanglesUseCase())
            } else {
                emit(CallResult.success(savedData))
            }

        } ?: emit(getRectanglesUseCase())
    }

    fun saveChanges(rectangleWithPos: List<RectangleWithPos>) {
        sharedPreferencesHelper.persistRectanglesPositions(rectangleWithPos)
        saveDate()
    }

    private fun saveDate() {
        val today = Calendar.getInstance().time.toString(DEFAULT_DATE_FORMAT)

        sharedPreferencesHelper.persistDate(today)
    }
}
