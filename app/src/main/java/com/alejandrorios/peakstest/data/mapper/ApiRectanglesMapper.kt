package com.alejandrorios.peakstest.data.mapper

import com.alejandrorios.peakstest.data.model.APIRectangle
import com.alejandrorios.peakstest.domain.model.Rectangle
import com.alejandrorios.peakstest.domain.model.RectangleWithPos

object ApiRectanglesMapper {

    fun mapAsRectangle(apiModel: APIRectangle): Rectangle = with(apiModel) {
        Rectangle(x, y, size)
    }

    fun mapAsRectangleWithPos(indexForId: Int, rectangle: Rectangle): RectangleWithPos =
        RectangleWithPos(indexForId, rectangle)
}
