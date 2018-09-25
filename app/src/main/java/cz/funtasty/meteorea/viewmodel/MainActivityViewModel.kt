package cz.funtasty.meteorea.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import cz.funtasty.meteorea.local.Meteorite
import cz.funtasty.meteorea.repo.NasaRepository
import cz.funtasty.meteorea.repo.Repository
import io.reactivex.Flowable
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async

class MainActivityViewModel : BaseActivityViewModel() {
    val mRepository: Repository = NasaRepository
    lateinit var onLoadMeteorites: Flowable<List<Meteorite>>

    init {
        GlobalScope.async {
            onLoadMeteorites = mDataHelper.db.meteoriteDao().getAll()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (!mDataHelper.isActualDatabase()) {
            GlobalScope.async {
                val meteorites = mRepository.getMeteorites().await()
                Log.d("repo", "loaded ${meteorites.size} meteorites")
                mDataHelper.updateMeteorities(meteorites)
                mDataHelper.setActualDatabase()
            }
        }
    }
}
