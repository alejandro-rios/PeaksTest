package com.alejandrorios.peakstest.domain.usecase

import com.alejandrorios.peakstest.data.utils.CallResult
import com.alejandrorios.peakstest.data.utils.NetworkErrorException
import com.alejandrorios.peakstest.domain.model.Rectangle
import com.alejandrorios.peakstest.domain.model.RectangleWithPos
import com.alejandrorios.peakstest.domain.repository.PeaksRepository
import com.alejandrorios.peakstest.utils.MockableTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class GetRectanglesUseCaseTest : MockableTest {

    @MockK
    private lateinit var peaksRepository: PeaksRepository

    private val expectedFailedResult = CallResult.failure<Throwable>(NetworkErrorException("Some error message"))
    private val expectedSuccessResult = CallResult.success(
        listOf(
            RectangleWithPos(
                id = 1,
                rectangle = Rectangle(0.4, 0.4, 0.6),
                lastPosX = null,
                lastPosY = null
            )
        )
    )

    @Test
    fun `When useCase is invoked then it returns a list of RectangleWithPos`() {
        coEvery {
            peaksRepository.getRectanglesFromAPI()
        } returns expectedSuccessResult

        val useCase = GetRectanglesUseCase(peaksRepository)

        val result = runBlocking {
            useCase()
        }

        result shouldBeEqualTo expectedSuccessResult

        coVerify {
            peaksRepository.getRectanglesFromAPI()
        }
    }

    @Test
    fun `When useCase is invoked then it returns Failure`() {
        coEvery {
            peaksRepository.getRectanglesFromAPI()
        } returns expectedFailedResult

        val useCase = GetRectanglesUseCase(peaksRepository)

        val result = runBlocking {
            useCase()
        }

        result shouldBeEqualTo expectedFailedResult

        coVerify {
            peaksRepository.getRectanglesFromAPI()
        }
    }
}
