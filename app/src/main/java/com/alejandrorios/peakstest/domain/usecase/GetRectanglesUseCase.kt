package com.alejandrorios.peakstest.domain.usecase

import com.alejandrorios.peakstest.data.utils.CallResult
import com.alejandrorios.peakstest.domain.model.RectangleWithPos
import com.alejandrorios.peakstest.domain.repository.PeaksRepository

class GetRectanglesUseCase(private val peaksRepository: PeaksRepository) {

    suspend operator fun invoke(): CallResult<List<RectangleWithPos>> = peaksRepository.getRectanglesFromAPI()
}


