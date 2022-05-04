package com.alejandrorios.peakstest.data.repository

import com.alejandrorios.peakstest.data.model.APIRectangle
import com.alejandrorios.peakstest.data.service.PeaksService
import com.alejandrorios.peakstest.utils.MockableTest
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeaksRepositoryTest : MockableTest {

    @MockK
    lateinit var peaksService: PeaksService

    private lateinit var repository: PeaksRepositoryImpl

    @Before
    override fun setup() {
        super.setup()
        repository = PeaksRepositoryImpl(peaksService)
    }

    @Test
    fun `When getRectanglesFromAPI is called then it returns Call-List APIRectangle`() {
        val call = mockk<Call<List<APIRectangle>>>()
        val responseData = mockk<Response<List<APIRectangle>>>(relaxed = true)
        val slot = CapturingSlot<Callback<List<APIRectangle>>>()

        coEvery {
            peaksService.getRectanglesFromAPI()
        } returns call

        coEvery {
            responseData.isSuccessful
        } returns true

        coEvery {
            responseData.body()
        } returns mockk(relaxed = true)

        every { call.enqueue(capture(slot)) } answers {
            slot.captured.onResponse(call, responseData)
        }

        runBlocking {
            repository.getRectanglesFromAPI()
        }

        coVerify {
            peaksService.getRectanglesFromAPI()
            call.enqueue(any())
        }
    }
}
