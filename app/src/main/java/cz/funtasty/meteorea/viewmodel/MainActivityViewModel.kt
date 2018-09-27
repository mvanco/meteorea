package cz.funtasty.meteorea.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import cz.funtasty.meteorea.entity.Meteorite
import io.reactivex.Flowable
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.withContext

class MainActivityViewModel : BaseActivityViewModel() {
    lateinit var onLoadMeteorites: Flowable<List<Meteorite>>
    var onLoaderVisibilityChanged = MutableLiveData<Boolean>()

    init {
        GlobalScope.async {
            onLoadMeteorites = mDataHelper.db.meteoriteDao().getAll()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (!mDataHelper.isActualDatabase()) {
            onLoaderVisibilityChanged.value = true
            GlobalScope.async {
                val meteorites = mRepository.getMeteorites().await()
                mDataHelper.updateMeteorities(meteorites)
                mDataHelper.setActualDatabase()

                withContext(Dispatchers.Main) {
                    onLoaderVisibilityChanged.value = false
                }
            }
        }
    }
}
