package cz.funtasty.meteorea.api

import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Header

interface NasaApi {
    @GET("/resource/y77d-th95.json")
    fun getMeteorites(@Header("X-App-Token") token: String): Deferred<List<MeteoriteApi>>
}