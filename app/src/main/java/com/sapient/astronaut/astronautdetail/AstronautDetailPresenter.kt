package com.sapient.astronaut.astronautdetail

import com.sapient.astronaut.base.BasePreseneter
import com.sapient.astronaut.di.ActivityScope
import com.sapient.astronaut.model.Astronaut
import com.sapient.astronaut.service.AstronautService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class AstronautDetailPresenter @Inject constructor(astronautService: AstronautService): BasePreseneter<AstronautDetailView>() {

    private val astronautService: AstronautService = astronautService


    fun getAstronautDetail(astronautId: Int){
        astronautService.getAstronautDetail(astronautId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<Astronaut>(){
                override fun onComplete() {
                    Timber.i("Astronaut detail complete block")
                }

                override fun onNext(astronaut: Astronaut) {
                    Timber.i("Astronaut detail successful response received")
                    getView().updateAstronautDetails(astronaut)
                    getView().updateAstronautDetailViewVisibility()
                }

                override fun onError(e: Throwable) {
                    Timber.e("Astronaut detail error response received $e")
                    getView().displayError()
                }
            })

    }
}