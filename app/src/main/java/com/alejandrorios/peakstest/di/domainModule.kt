package com.alejandrorios.peakstest.di

import com.alejandrorios.peakstest.BuildConfig
import com.alejandrorios.peakstest.data.repository.PeaksRepositoryImpl
import com.alejandrorios.peakstest.data.repository.PeaksRepositoryMock
import com.alejandrorios.peakstest.domain.repository.PeaksRepository
import com.alejandrorios.peakstest.domain.usecase.GetRectanglesUseCase
import org.koin.dsl.module

val domainModule = module {
    // Repositories
    single<PeaksRepository> { PeaksRepositoryImpl(get()) }

    // UseCases
    single { GetRectanglesUseCase(wrapMock(get())) }
}

/**
 * Simple wrapper to mock a retrofit call
 *
 * Reference: https://proandroiddev.com/how-to-mock-retrofit-api-calls-or-any-other-interface-53161eee6789
 */
fun wrapMock(actual: PeaksRepository): PeaksRepository =
    if (BuildConfig.DEBUG) PeaksRepositoryMock(actual) else actual
