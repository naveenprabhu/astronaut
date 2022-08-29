package com.sapient.astronaut.astronautdetail

import com.sapient.astronaut.di.ActivityScope
import dagger.Subcomponent

@Subcomponent
@ActivityScope
interface AstronautDetailComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AstronautDetailComponent
    }

    fun inject(activity: AstronautDetailActivity)

}