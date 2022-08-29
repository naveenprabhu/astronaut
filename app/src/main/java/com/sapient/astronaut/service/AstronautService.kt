package com.sapient.astronaut.service

import com.sapient.astronaut.model.Astronaut
import com.sapient.astronaut.model.AstronautList
import com.sapient.astronaut.utils.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface AstronautService {

    @GET(Constants.URL_ASTRONAUT_LIST)
    fun getAstronauts() : Observable<AstronautList>

    @GET(Constants.URL_ASTRONAUT_DETAIL)
    fun getAstronautDetail(@Path("id") astronautId: Int) : Observable<Astronaut>
}