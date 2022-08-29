package com.sapient.astronaut.astronautlist

import com.sapient.astronaut.base.BasePreseneter
import com.sapient.astronaut.di.ActivityScope
import com.sapient.astronaut.model.Astronaut
import com.sapient.astronaut.model.AstronautList
import com.sapient.astronaut.service.AstronautService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


@ActivityScope
class AstronautListPresenter @Inject constructor(astronautService: AstronautService) : BasePreseneter<AstronautListView>() {

    private val astronautService: AstronautService = astronautService
    var astronautList: List<Astronaut>? = null

    fun getAstronauts() {
         astronautService.getAstronauts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<AstronautList>(){
                override fun onComplete() {
                    getView().dismissSwipeRefresh()
                    Timber.i("Astronaut list complete block executed")
                }

                override fun onNext(astronauts: AstronautList) {
                    Timber.i("Astronaut list success response received")
                    astronautList = astronauts.results
                    getView().updateAdapter()
                }

                override fun onError(e: Throwable) {
                    Timber.e("Astronaut list error response received $e")
                    getView().displayError()
                    getView().dismissSwipeRefresh()
                }

            })
    }

    fun sortByName(isAscending: Boolean) {

        astronautList = if(isAscending) {
            astronautList?.sortedBy {
                it.name
            }
        } else {
            astronautList?.sortedByDescending {
                it.name
            }
        }

        getView().updateAdapter()

    }

    fun getAstronautAtPosition(position: Int): Astronaut {
        Timber.d("Astronaut list adapter at position $position")
        return astronautList?.get(position) ?: Astronaut()
    }
}