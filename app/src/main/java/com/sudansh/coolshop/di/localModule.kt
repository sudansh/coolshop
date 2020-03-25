package com.sudansh.coolshop.di

import com.sudansh.coolshop.data.local.Prefs
import com.sudansh.coolshop.data.local.UserManager
import com.sudansh.coolshop.data.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single { UserRepository(get(), get()) }
    single { Prefs(androidContext()) }
    single { UserManager(get()) }
}