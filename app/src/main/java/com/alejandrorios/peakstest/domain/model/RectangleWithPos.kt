package com.alejandrorios.peakstest.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RectangleWithPos(
    val id: Int,
    val rectangle: Rectangle,
    var lastPosX: Double? = null,
    var lastPosY: Double? = null
)
