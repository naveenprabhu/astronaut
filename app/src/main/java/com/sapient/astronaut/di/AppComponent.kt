package com.sapient.astronaut.di

import android.content.Context
import com.sapient.astronaut.interceptor.ForceCacheInterceptor
import com.sapient.astronaut.astronautdetail.AstronautDetailComponent
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

    fun astronautDetailComponent() : AstronautDetailComponent.Factory

    fun forceCacheInterceptor() : ForceCacheInterceptor

}
