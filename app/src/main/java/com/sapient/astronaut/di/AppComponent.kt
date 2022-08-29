package com.sapient.astronaut.di

import android.content.Context
import com.sapient.astronaut.ForceCacheInterceptor
import com.sapient.astronaut.astronautlist.AstronautListComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppSubcomponents::class, NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun astronautListComponent() : AstronautListComponent.Factory


    fun forceCacheInterceptor() : ForceCacheInterceptor

}
