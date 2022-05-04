package com.alejandrorios.peakstest.domain.repository

import com.alejandrorios.peakstest.data.utils.CallResult
import com.alejandrorios.peakstest.domain.model.RectangleWithPos

interface PeaksRepository {

    suspend fun getRectanglesFromAPI(): CallResult<List<RectangleWithPos>>
}
