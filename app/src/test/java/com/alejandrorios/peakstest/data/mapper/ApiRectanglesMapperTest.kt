package com.alejandrorios.peakstest.data.mapper

import com.alejandrorios.peakstest.data.model.APIRectangle
import com.alejandrorios.peakstest.domain.model.Rectangle
import com.alejandrorios.peakstest.domain.model.RectangleWithPos
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class ApiRectanglesMapperTest {

    @Test
    fun `When mapAsRectangle is called then should return Rectangle`() {
        val apiRectangle = APIRectangle(0.4, 0.4, 0.6)

        val result = ApiRectanglesMapper.mapAsRectangle(apiRectangle)

        result shouldBeEqualTo Rectangle(0.4, 0.4, 0.6)
    }

    @Test
    fun `When mapAsRectangleWithPos is called then should return RectangleWithPos`() {
        val rectangle = Rectangle(0.4, 0.4, 0.6)

        val result = ApiRectanglesMapper.mapAsRectangleWithPos(1, rectangle)

        result shouldBeEqualTo RectangleWithPos(1, Rectangle(0.4, 0.4, 0.6), null, null)
    }
}
