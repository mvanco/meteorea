package cz.funtasty.meteorea.viewmodel

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import cz.funtasty.meteorea.entity.DataHelper
import cz.funtasty.meteorea.repo.NasaRepository
import cz.funtasty.meteorea.repo.Repository

abstract class BaseActivityViewModel : ViewModel(), LifecycleObserver {
    val mDataHelper = DataHelper.sInstance
    val mRepository: Repository = NasaRepository
}
