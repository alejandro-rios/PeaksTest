package com.alejandrorios.peakstest.data.repository

import com.alejandrorios.peakstest.data.mapper.ApiRectanglesMapper
import com.alejandrorios.peakstest.data.model.APIRectangle
import com.alejandrorios.peakstest.data.service.PeaksService
import com.alejandrorios.peakstest.data.utils.CallResult
import com.alejandrorios.peakstest.data.utils.NetworkErrorException
import com.alejandrorios.peakstest.data.utils.handleResponse
import com.alejandrorios.peakstest.domain.model.RectangleWithPos
import com.alejandrorios.peakstest.domain.repository.PeaksRepository
import retrofit2.Call
import retrofit2.Callback
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PeaksRepositoryImpl(private val peaksService: PeaksService): PeaksRepository {

    override suspend fun getRectanglesFromAPI(): CallResult<List<RectangleWithPos>> = suspendCoroutine { continuation ->
        val call = peaksService.getRectanglesFromAPI()

        call.enqueue(object : Callback<List<APIRectangle>> {
            override fun onResponse(call: Call<List<APIRectangle>>, response: retrofit2.Response<List<APIRectangle>>) {
                response.handleResponse(
                    continuation = continuation,
                    transformation = { apiResponse ->
                        val rectangles = apiResponse.map {
                            ApiRectanglesMapper.mapAsRectangle(it)
                        }

                        rectangles.mapIndexed { index, rectangle ->
                            ApiRectanglesMapper.mapAsRectangleWithPos(index, rectangle)
                        }
                    }
                )
            }

            override fun onFailure(call: Call<List<APIRectangle>>, t: Throwable) {
                continuation.resume(CallResult.failure<NetworkErrorException>(t.fillInStackTrace()))
            }
        })
    }
}
