package com.sapient.astronaut.astronautlist

import com.sapient.astronaut.di.ActivityScope
import dagger.Subcomponent

@Subcomponent
@ActivityScope
interface AstronautListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AstronautListComponent
    }

    fun inject(activity: AstronautListActivity)

    fun inject(adapter: AstronautAdapter)
}