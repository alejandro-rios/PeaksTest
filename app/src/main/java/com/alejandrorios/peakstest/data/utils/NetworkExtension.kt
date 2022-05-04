package com.alejandrorios.peakstest.data.utils

import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

inline fun <param, result> retrofit2.Response<param>.handleResponse(
    continuation: Continuation<CallResult<result>>,
    transformation: (predicate: param) -> result
) {
    with(continuation) {
        if (!isSuccessful || errorBody() != null) {
            resume(CallResult.failure<NetworkErrorException>(NetworkErrorException(errorBody()?.string())))
            return
        }

        try {
            resume(CallResult.success(transformation(body()!!)))
        } catch (t: Throwable) {
            resume(CallResult.failure<Throwable>(t))
        }
    }
}
