package cz.funtasty.meteorea.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import cz.funtasty.meteorea.entity.Meteorite
import io.reactivex.Flowable
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async

class MainActivityViewModel : BaseActivityViewModel() {
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
                mDataHelper.updateMeteorities(meteorites)
                mDataHelper.setActualDatabase()
            }
        }
    }
}
