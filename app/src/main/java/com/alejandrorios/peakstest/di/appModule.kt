package com.alejandrorios.peakstest.di

import com.alejandrorios.peakstest.data.SharedPreferencesHelper
import com.alejandrorios.peakstest.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Helpers
    single { SharedPreferencesHelper(androidContext()) }

    // ViewModels
    viewModel { MainViewModel(get(), get()) }
}
