package com.alejandrorios.peakstest.data.repository

import com.alejandrorios.peakstest.data.utils.CallResult
import com.alejandrorios.peakstest.domain.model.Rectangle
import com.alejandrorios.peakstest.domain.model.RectangleWithPos
import com.alejandrorios.peakstest.domain.repository.PeaksRepository

/**
 * Part of a simple wrapper to mock a retrofit call
 *
 * Reference: https://proandroiddev.com/how-to-mock-retrofit-api-calls-or-any-other-interface-53161eee6789
 */
class PeaksRepositoryMock(private val actual: PeaksRepository): PeaksRepository by actual {

    override suspend fun getRectanglesFromAPI(): CallResult<List<RectangleWithPos>> {
        val rectangles = listOf(
            Rectangle(x = 0.5, y = 0.5, size = 0.2),
            Rectangle(x = 0.7, y = 0.7, size = 0.2)
        )

        val rectanglesWithPos = rectangles.mapIndexed { index, rectangle ->
            RectangleWithPos(id = index, rectangle = rectangle)
        }

        return CallResult.Success(rectanglesWithPos)
    }
}
