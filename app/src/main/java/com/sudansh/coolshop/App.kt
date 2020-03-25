package com.sudansh.coolshop

import android.app.Application
import com.sudansh.coolshop.di.viewModelModule
import com.sudansh.coolshop.di.localModule
import com.sudansh.coolshop.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

	override fun onCreate() {
		super.onCreate()
		startKoin {
			// declare used Android context
			androidContext(this@App)
			// declare modules
			modules(listOf(remoteModule, localModule, viewModelModule))
		}
	}
}