package cz.funtasty.meteorea.repo

import cz.funtasty.meteorea.Config
import cz.funtasty.meteorea.api.MeteoriteApi
import cz.funtasty.meteorea.api.NasaApi
import kotlinx.coroutines.experimental.Deferred

object NasaRepository: Repository {
    private val mApiService = RetrofitClient.createService(NasaApi::class.java)

    override fun getMeteorites(): Deferred<List<MeteoriteApi>> {
        return mApiService.getMeteorites(Config.APP_TOKEN)
    }
}