package com.sudansh.coolshop.di

import com.sudansh.coolshop.ui.detail.MainViewModel
import com.sudansh.coolshop.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
}