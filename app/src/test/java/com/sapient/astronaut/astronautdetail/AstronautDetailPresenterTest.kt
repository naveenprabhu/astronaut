package com.sapient.astronaut.astronautdetail

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.then
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sapient.astronaut.model.Astronaut
import com.sapient.astronaut.service.AstronautService
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class AstronautDetailPresenterTest {

    @Mock
    lateinit var astronautService: AstronautService


    lateinit var presenter: AstronautDetailPresenter

    @Mock
    lateinit var astronautDetailView: AstronautDetailView

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        presenter = AstronautDetailPresenter(astronautService)
        presenter.setView(astronautDetailView)
    }


    @Test
    fun shouldMakeNetworkCallToFetchAstronauts() {
        var astronaut = Astronaut()

        whenever(astronautService.getAstronautDetail(1)).thenReturn(Observable.just(astronaut))

        presenter.getAstronautDetail(1)
        verify(astronautService).getAstronautDetail(1)
    }


    @Test
    fun shouldReturnSuccessfulResponseAndUpdateTheAdapter() {

        val astronautId = 1
        var astronaut = getMockAstronautDetail(astronautId)

        whenever(astronautService.getAstronautDetail(astronautId)).thenReturn(Observable.just(astronaut))
        presenter.getAstronautDetail(astronautId)

        var captor = argumentCaptor<Astronaut>()

        then(verify(presenter.getView()).updateAstronautDetails(captor.capture()))
        then(verify(presenter.getView()).updateAstronautDetailViewVisibility())

        then(Assert.assertThat(captor.firstValue, CoreMatchers.`is`(astronaut)))
    }


    @Test
    fun shouldResponseFailAndDisplayError() {

        val astronautId = 1

        whenever(astronautService.getAstronautDetail(astronautId)).thenReturn(
            Observable.error(
                IOException("Something went wrong")
            ))

        presenter.getAstronautDetail(astronautId)

        then(verify(presenter.getView()).displayError())
    }

    private fun getMockAstronautDetail(astronautId: Int) = Astronaut(
        astronautId, "Franz Viehböck",
        "Australian",
        "Franz Artur Viehböck (born August 24, 1960 in Vienna) is an Austrian electrical engineer, and was Austria's first cosmonaut. He was titulated „Austronaut“ by his country's media. He visited the Mir space station in 1991 aboard Soyuz TM-13, returning aboard Soyuz TM-12 after spending just over a week in space.",
        "https://spacelaunchnow-prod-east.nyc3.cdn.digitaloceanspaces.com/media/default/cache/54/57/5457ce75acb7b188196eb442e3f17b64.jpg",
        "https://spacelaunchnow-prod-east.nyc3.cdn.digitaloceanspaces.com/media/astronaut_images/franz2520viehb25c325b6ck_image_20181201223901.jpg"
    )
}