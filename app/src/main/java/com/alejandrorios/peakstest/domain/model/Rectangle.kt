package com.alejandrorios.peakstest.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Rectangle(
    val x: Double,
    val y: Double,
    val size: Double
)
