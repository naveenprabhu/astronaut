package com.sapient.astronaut.astronautlist

import com.nhaarman.mockitokotlin2.then
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sapient.astronaut.astronautlist.AstronautListPresenter
import com.sapient.astronaut.astronautlist.AstronautListView
import com.sapient.astronaut.model.Astronaut
import com.sapient.astronaut.model.AstronautList
import com.sapient.astronaut.service.AstronautService
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class AstronautListPresenterTest {

    @Mock
    lateinit var astronautService: AstronautService

    @Mock
    lateinit var astronautListView: AstronautListView

    lateinit var presenter: AstronautListPresenter

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        presenter = AstronautListPresenter(astronautService)
        presenter.setView(astronautListView)
    }

    @Test
    fun shouldMakeNetworkCallToFetchAstronauts() {
        var astronauts = listOf<Astronaut>(
            Astronaut()
        )
        var astronautList = AstronautList(1, astronauts)
        whenever(astronautService.getAstronauts()).thenReturn(Observable.just(astronautList))

        presenter.getAstronauts()
        verify(astronautService).getAstronauts()
    }


    @Test
    fun shouldReturnSuccessfulResponseandUpdateTheAdapter() {

        var expectedAstronauts = listOf<Astronaut>(
            Astronaut()
        )
        var expectedAstronautList = AstronautList(1, expectedAstronauts)
        whenever(astronautService.getAstronauts()).thenReturn(Observable.just(expectedAstronautList))
        presenter.getAstronauts()

        then(verify(presenter.getView()).updateAdapter())
        then(verify(presenter.getView()).dismissSwipeRefresh())
        val actualAstronautList = presenter.astronautList
        then(assertThat(actualAstronautList, `is`(expectedAstronauts)))
    }


    @Test
    fun shouldResponseFailAndDisplayError() {

        var expectedAstronauts = listOf<Astronaut>(
            Astronaut()
        )
        var expectedAstronautList = AstronautList(1, expectedAstronauts)

        whenever(astronautService.getAstronauts()).thenReturn(Observable.error(IOException("Something went wrong")))

        presenter.getAstronauts()

        then(verify(presenter.getView()).displayError())
        then(verify(presenter.getView()).dismissSwipeRefresh())
    }

    @Test
    fun shouldSortByAscending() {

        var astronauts = listOf<Astronaut>(
            Astronaut(name = "Roberta"),
            Astronaut(name = "Marcos"),
            Astronaut(name = "Franz")
        )

        var expectedAstronautList = AstronautList(1, astronauts)
        whenever(astronautService.getAstronauts()).thenReturn(Observable.just(expectedAstronautList))
        presenter.getAstronauts()

        val expectedAstronauts = listOf<Astronaut>(
            Astronaut(name = "Franz"),
            Astronaut(name = "Marcos"),
            Astronaut(name = "Roberta")
        )
        presenter.sortByName(true)

        then(assertThat(presenter.astronautList, `is`(expectedAstronauts)))
    }

    @Test
    fun shouldSortByDescending() {

        var astronauts = listOf<Astronaut>(
            Astronaut(name = "Franz"),
            Astronaut(name = "Marcos"),
            Astronaut(name = "Roberta")
        )

        var expectedAstronautList = AstronautList(1, astronauts)
        whenever(astronautService.getAstronauts()).thenReturn(Observable.just(expectedAstronautList))
        presenter.getAstronauts()

        val expectedAstronauts = listOf<Astronaut>(
            Astronaut(name = "Roberta"),
            Astronaut(name = "Marcos"),
            Astronaut(name = "Franz")
        )
        presenter.sortByName(false)

        then(assertThat(presenter.astronautList, `is`(expectedAstronauts)))
    }





}