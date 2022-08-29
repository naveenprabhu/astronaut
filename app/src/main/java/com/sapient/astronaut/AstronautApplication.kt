package com.sapient.astronaut

import android.app.Application
import com.sapient.astronaut.di.AppComponent
import com.sapient.astronaut.di.DaggerAppComponent

import timber.log.Timber

class AstronautApplication: Application() {

    val appComponent: AppComponent by lazy { initalizeAppComponent() }

    private fun initalizeAppComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}