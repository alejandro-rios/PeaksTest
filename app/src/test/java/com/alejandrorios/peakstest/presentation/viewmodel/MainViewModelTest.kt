package com.alejandrorios.peakstest.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alejandrorios.peakstest.data.SharedPreferencesHelper
import com.alejandrorios.peakstest.data.utils.CallResult
import com.alejandrorios.peakstest.domain.model.RectangleWithPos
import com.alejandrorios.peakstest.domain.usecase.GetRectanglesUseCase
import com.alejandrorios.peakstest.presentation.MainViewModel
import com.alejandrorios.peakstest.utils.MockableTest
import com.alejandrorios.peakstest.utils.observeForTesting
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class MainViewModelTest: MockableTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    @MockK
    lateinit var getRectanglesUseCase: GetRectanglesUseCase

    @MockK
    lateinit var getObserver : Observer<CallResult<List<RectangleWithPos>>>

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val expectedGetRectanglesSuccessResult = CallResult.success(mockk<List<RectangleWithPos>>())
    private lateinit var viewModel: MainViewModel

    @Before
    override fun setup() {
        super.setup()
        Dispatchers.setMain(mainThreadSurrogate)

        viewModel = MainViewModel(sharedPreferencesHelper, getRectanglesUseCase)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `When validateAndStart is called then returns CallResult-List RectanglesWithPos`() {
        every {
            sharedPreferencesHelper.getPersistedDate()
        } returns null

        coEvery {
            getRectanglesUseCase.invoke()
        } returns expectedGetRectanglesSuccessResult

        viewModel.validateAndStart().observeForTesting(getObserver) {
            coVerifySequence {
                getRectanglesUseCase.invoke()
                getObserver.onChanged(expectedGetRectanglesSuccessResult)
            }
        }
    }
}
