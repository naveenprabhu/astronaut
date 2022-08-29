package com.sapient.astronaut.astronautdetail

import com.sapient.astronaut.model.Astronaut

interface AstronautDetailView {

    fun updateAstronautDetails(astronaut: Astronaut)

    fun updateAstronautDetailViewVisibility()

    fun displayError()
}
