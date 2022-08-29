package com.sapient.astronaut.di

import com.sapient.astronaut.astronautlist.AstronautListComponent
import dagger.Module

@Module(
    subcomponents = [
    AstronautListComponent::class
    ]
)
class AppSubcomponents