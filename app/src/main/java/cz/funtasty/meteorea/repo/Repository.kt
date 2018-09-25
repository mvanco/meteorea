package cz.funtasty.meteorea.repo

import cz.funtasty.meteorea.api.MeteoriteApi
import kotlinx.coroutines.experimental.Deferred

interface Repository {
    fun getMeteorites(): Deferred<List<MeteoriteApi>>
}