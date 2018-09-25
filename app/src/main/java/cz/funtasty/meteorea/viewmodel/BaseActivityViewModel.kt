package cz.funtasty.meteorea.viewmodel

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import cz.funtasty.meteorea.local.DataHelper

open class BaseActivityViewModel : ViewModel(), LifecycleObserver {
    val mDataHelper = DataHelper.sInstance
}
