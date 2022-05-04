package com.alejandrorios.peakstest.data.service

import com.alejandrorios.peakstest.data.model.APIRectangle
import retrofit2.Call
import retrofit2.http.GET

interface PeaksService {

    @GET("rectangles")
    fun getRectanglesFromAPI(): Call<List<APIRectangle>>
}
