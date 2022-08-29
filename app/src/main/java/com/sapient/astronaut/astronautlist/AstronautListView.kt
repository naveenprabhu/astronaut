package com.sapient.astronaut.astronautlist

interface AstronautListView {
    fun updateAdapter()
    fun displayError()
    fun dismissSwipeRefresh()

}